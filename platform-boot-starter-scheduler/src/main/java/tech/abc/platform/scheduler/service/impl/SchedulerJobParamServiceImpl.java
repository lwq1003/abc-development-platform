package tech.abc.platform.scheduler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.scheduler.entity.SchedulerJobParam;
import tech.abc.platform.scheduler.mapper.SchedulerJobParamMapper;
import tech.abc.platform.scheduler.service.SchedulerJobParamService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度任务参数 服务实现类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@Service
@Slf4j
public class SchedulerJobParamServiceImpl extends BaseServiceImpl<SchedulerJobParamMapper, SchedulerJobParam> implements SchedulerJobParamService {
    @Override
    public SchedulerJobParam init() {
        SchedulerJobParam entity = new SchedulerJobParam();
        // 默认值处理
        entity.setParamName("");
        return entity;
    }

    @Override
    public void beforeAdd(SchedulerJobParam entity) {
        // 唯一性验证
        // 验证 参数名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamName())) {
            long countParamName = this.lambdaQuery().eq(SchedulerJobParam::getParamName, entity.getParamName())
                    .eq(SchedulerJobParam::getSchedulerJob, entity.getSchedulerJob()).count();
            if (countParamName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【参数名称】");
            }
        }
        // 验证 参数编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamCode())) {
            long countParamCode = this.lambdaQuery().eq(SchedulerJobParam::getParamCode, entity.getParamCode())
                    .eq(SchedulerJobParam::getSchedulerJob, entity.getSchedulerJob()).count();
            if (countParamCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【参数编码】");
            }
        }
    }

    @Override
    public void beforeModify(SchedulerJobParam entity) {
        // 唯一性验证
        // 验证 参数名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamName())) {
            long countParamName = this.lambdaQuery().eq(SchedulerJobParam::getParamName, entity.getParamName())
                    .eq(SchedulerJobParam::getSchedulerJob, entity.getSchedulerJob())
                    .ne(SchedulerJobParam::getId, entity.getId()).count();
            if (countParamName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【参数名称】");
            }
        }
        // 验证 参数编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamCode())) {
            long countParamCode = this.lambdaQuery().eq(SchedulerJobParam::getParamCode, entity.getParamCode())
                    .eq(SchedulerJobParam::getSchedulerJob, entity.getSchedulerJob())
                    .ne(SchedulerJobParam::getId, entity.getId()).count();
            if (countParamCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【参数编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<SchedulerJobParam> list = this.lambdaQuery().in(SchedulerJobParam::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getParamName());
                });
            }
        }
        return result;
    }

    @Override
    public List<SchedulerJobParam> getBySchedulerJobId(String schedulerJobId) {
        return this.lambdaQuery().eq(SchedulerJobParam::getSchedulerJob, schedulerJobId).list();
        
    }

    @Override
    protected void copyPropertyHandle(SchedulerJobParam entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setParamName(entity.getParamName() + " 副本");
    }

}

