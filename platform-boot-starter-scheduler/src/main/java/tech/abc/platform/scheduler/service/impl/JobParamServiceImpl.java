package tech.abc.platform.scheduler.service.impl;

import tech.abc.platform.scheduler.entity.JobParam;
import tech.abc.platform.scheduler.mapper.JobParamMapper;
import tech.abc.platform.scheduler.service.JobParamService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
/**
* 任务参数 服务实现类
*
* @author wqliu
* @date 2023-05-24
*/
@Service
@Slf4j
public class JobParamServiceImpl extends BaseServiceImpl<JobParamMapper, JobParam> implements JobParamService {
    @Override
    public JobParam init() {
        JobParam entity=new JobParam();
        //默认值处理
        entity.setParamName("");
        return entity;
    }

    @Override
    public void beforeAdd(JobParam entity) {
        //唯一性验证
        //验证 参数名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamName())) {
            long countParamName = this.lambdaQuery().eq(JobParam::getParamName, entity.getParamName())
            .eq(JobParam::getJob, entity.getJob()).count();
            if (countParamName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【参数名称】");
            }
        }
        //验证 参数编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamCode())) {
            long countParamCode = this.lambdaQuery().eq(JobParam::getParamCode, entity.getParamCode())
            .eq(JobParam::getJob, entity.getJob()).count();
            if (countParamCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【参数编码】");
            }
        }
    }

    @Override
    public void beforeModify(JobParam entity) {
        //唯一性验证
        //验证 参数名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamName())) {
            long countParamName = this.lambdaQuery().eq(JobParam::getParamName, entity.getParamName())
                .eq(JobParam::getJob, entity.getJob())
                .ne(JobParam::getId, entity.getId()).count();
            if (countParamName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【参数名称】");
            }
        }
        //验证 参数编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getParamCode())) {
            long countParamCode = this.lambdaQuery().eq(JobParam::getParamCode, entity.getParamCode())
                .eq(JobParam::getJob, entity.getJob())
                .ne(JobParam::getId, entity.getId()).count();
            if (countParamCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【参数编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<JobParam> list = this.lambdaQuery().in(JobParam::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getParamName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(JobParam entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setParamName (entity.getParamName() + " 副本");
    }

}

