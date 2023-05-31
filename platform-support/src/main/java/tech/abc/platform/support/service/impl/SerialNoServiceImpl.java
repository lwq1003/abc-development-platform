package tech.abc.platform.support.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.SerialNo;
import tech.abc.platform.support.exception.SerialNoExceptionEnum;
import tech.abc.platform.support.mapper.SerialNoMapper;
import tech.abc.platform.support.service.SerialNoService;
import tech.abc.platform.system.enums.SerialNoResetStrategyEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流水号 服务实现类
 *
 * @author wqliu
 * @date 2023-05-30
 */
@Service
@Slf4j
public class SerialNoServiceImpl extends BaseServiceImpl<SerialNoMapper, SerialNo> implements SerialNoService {
    @Override
    public SerialNo init() {
        SerialNo entity = new SerialNo();
        // 默认值处理
        entity.setConnector("");
        entity.setCurrentValue(1);
        entity.setResetStrategy("");
        return entity;
    }

    @Override
    public void beforeAdd(SerialNo entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(SerialNo::getName, entity.getName())
                    .eq(SerialNo::getModule, entity.getModule()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(SerialNo::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(SerialNo entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(SerialNo::getName, entity.getName())
                    .eq(SerialNo::getModule, entity.getModule())
                    .ne(SerialNo::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(SerialNo::getCode, entity.getCode())
                    .ne(SerialNo::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<SerialNo> list = this.lambdaQuery().in(SerialNo::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(SerialNo entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 100L, multiplier = 2))
    public String generateSingle(String code) {
        List<String> list = generateBatch(code, 1);
        return list.get(0);
    }

    /**
     * 达到最大重试次数
     * recover 机制
     *
     * @param e 异常
     */
    @Recover
    public String recoverSingle(Exception e, String code) {
        log.error("生成单据流水号达到最大重试次数", e);
        throw new CustomException(CommonException.TRY_MAX_COUNT);
    }


    /**
     * 批量获取流水号
     * <p>
     * value：抛出指定异常才会重试
     * include：和value一样，默认为空，当exclude也为空时，默认所有异常
     * exclude：指定不处理的异常
     * maxAttempts：最大重试次数，默认3次
     * backoff：重试等待策略，
     * 默认使用@Backoff，@Backoff的value默认为1000L，我们设置为100； 以毫秒为单位的延迟（默认 1000）
     * multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
     *
     * @param code  编码
     * @param count 数量
     * @return {@link List}<{@link String}>
     */
    @Override
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 100L, multiplier = 2))
    public List<String> generateBatch(String code, int count) {
        // 先根据编码获取当前流水号
        SerialNo entity = this.lambdaQuery().eq(SerialNo::getCode, code).one();
        if (entity == null) {
            throw new CustomException(SerialNoExceptionEnum.CODE_NOT_EXIST);
        }
        int currentSerialNo = entity.getCurrentValue();
        // 计算更新后的流水号
        int updateSerialNo = currentSerialNo + count;
        // 更新数据
        entity.setCurrentValue(updateSerialNo);
        boolean updateFlag = modify(entity);
        if (!updateFlag) {
            throw new CustomException(CommonException.UPDATE_ERROR);
        }
        // 组织返回
        String template = generateNoTemplate(entity);
        List<String> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(String.format(template, StringUtils.leftPad(String.valueOf(currentSerialNo), entity.getLength(),
                    '0')));
            currentSerialNo++;
        }
        return result;
    }

    /**
     * 达到最大重试次数
     *
     * @param e 异常
     */
    @Recover
    public List<String> recoverBatch(Exception e) {
        log.error("生成单据流水号达到最大重试次数", e);
        throw new CustomException(CommonException.TRY_MAX_COUNT);
    }


    /**
     * 重置流水号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetSerialNo() {
        List<SerialNo> list = super.list();
        for (SerialNo entity : list) {
            if (SerialNoResetStrategyEnum.DAY.name().equals(entity.getResetStrategy())) {
                // 定时器每天0时执行，所以如果是每天的话，直接重置就可以了
                doReset(entity);
            } else if (SerialNoResetStrategyEnum.MONTH.name().equals(entity.getResetStrategy())) {
                // 定时器每天运行一次，当运行时间是每月的第一天，则执行重置
                if (LocalDateTime.now().getDayOfMonth() == 1) {
                    doReset(entity);
                }
            } else if (SerialNoResetStrategyEnum.YEAR.name().equals(entity.getResetStrategy())) {
                // 定时器每天运行一次，当运行时间是每年的第一天，则执行重置
                if (LocalDateTime.now().getDayOfYear() == 1) {
                    doReset(entity);
                }
            }
        }
    }

    /**
     * 生成流水号模板
     * 流水号部分使用%s占位符
     *
     * @param serialNo
     * @return
     */
    private String generateNoTemplate(SerialNo serialNo) {
        StringBuilder no = new StringBuilder();
        if (StringUtils.isNotBlank(serialNo.getPrefix())) {
            no.append(serialNo.getPrefix()).append(serialNo.getConnector());
        }
        if (StringUtils.isNotBlank(serialNo.getDateFormat())) {
            no.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(serialNo.getDateFormat()))).append(serialNo.getConnector());
        }
        no.append("%s");
        return no.toString();
    }


    private void doReset(SerialNo entity) {
        entity.setCurrentValue(1);
        modify(entity);
    }

}

