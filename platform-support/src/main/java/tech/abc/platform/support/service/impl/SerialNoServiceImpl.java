package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
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
import tech.abc.platform.support.mapper.SerialNoMapper;
import tech.abc.platform.support.service.SerialNoService;
import tech.abc.platform.system.enums.SerialNoResetStrategyEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 单据流水号 服务实现类
 *
 * @author wqliu
 */
@Service
@Slf4j
public class SerialNoServiceImpl extends BaseServiceImpl<SerialNoMapper, SerialNo> implements SerialNoService {


    @Override
    @Retryable(value = {CustomException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100L, multiplier = 2))
    public String generateSingle(String code) {
        List<String> list = generateBatch(code, 1);
        return list.get(0);
    }

    @Override
    @Retryable(value = {CustomException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100L, multiplier = 2))
    public List<String> generateBatch(String code, int count) {
        // 先根据编码获取当前流水号
        SerialNo entity = this.lambdaQuery().eq(SerialNo::getCode, code).one();
        int currentSerialNo = entity.getSerialNo();
        // 计算更新后的流水号
        int updateSerialNo = currentSerialNo + count;
        // 更新数据
        entity.setSerialNo(updateSerialNo);
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
     * 达到最大重试次数,抛出了一个没有指定进行重试的异常
     * recover 机制
     *
     * @param e 异常
     */
    @Recover
    public List<String> recoverBatch(CustomException e) {
        log.error("生成单据流水号达到最大重试次数", e);
        throw new CustomException(CommonException.TRY_MAX_COUNT);
    }

    /**
     * 达到最大重试次数,抛出了一个没有指定进行重试的异常
     * recover 机制
     *
     * @param e 异常
     */
    @Recover
    public String recoverSingle(CustomException e, String code) {
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
            no.append(serialNo.getPrefix()).append(serialNo.getConnectors());
        }
        if (StringUtils.isNotBlank(serialNo.getDatePart())) {
            no.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(serialNo.getDatePart()))).append(serialNo.getConnectors());
        }
        no.append("%s");
        return no.toString();
    }


    private void doReset(SerialNo entity) {
        entity.setSerialNo(1);
        modify(entity);
    }

    @Override
    public SerialNo init() {
        SerialNo entity = new SerialNo();

        return entity;
    }


    @Override
    public void beforeAdd(SerialNo entity) {
        // 验证名称全局唯一
        QueryWrapper<SerialNo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SerialNo::getName, entity.getName());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }
        // 验证编码全局唯一
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SerialNo::getCode, entity.getCode());
        count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    @Override
    public void beforeModify(SerialNo entity) {
        // 验证名称全局唯一
        QueryWrapper<SerialNo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SerialNo::getName, entity.getName())
                .ne(SerialNo::getId, entity.getId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }
        // 验证编码全局唯一
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SerialNo::getCode, entity.getCode())
                .ne(SerialNo::getId, entity.getId());
        count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }

    /**
     * 数据不为空检查，检查通过后返回对象
     *
     * @param id
     * @return
     */
    public SerialNo getEntity(String id) {
        // 标识非空判断
        if (id == null) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        // 对象非空判断
        SerialNo entity = query(id);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

}
