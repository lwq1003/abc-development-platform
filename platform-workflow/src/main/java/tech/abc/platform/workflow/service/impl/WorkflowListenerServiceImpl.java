package tech.abc.platform.workflow.service.impl;

import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.workflow.entity.WorkflowListener;
import tech.abc.platform.workflow.mapper.WorkflowListenerMapper;
import tech.abc.platform.workflow.service.WorkflowListenerService;
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
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
/**
* 工作流监听器 服务实现类
*
* @author wqliu
* @date 2023-08-29
*/
@Service
@Slf4j
public class WorkflowListenerServiceImpl extends BaseServiceImpl<WorkflowListenerMapper, WorkflowListener> implements WorkflowListenerService {

    @Override
    public WorkflowListener init() {
        WorkflowListener entity=new WorkflowListener();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setCategory("TASK");
        entity.setType("CLASS");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowListener entity) {
        //唯一性验证
        //验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(WorkflowListener::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【名称】");
            }
        }

    }

    @Override
    public void beforeModify(WorkflowListener entity) {
        //唯一性验证
        //验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(WorkflowListener::getName, entity.getName())
                .ne(WorkflowListener::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【名称】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowListener> list = this.lambdaQuery().in(WorkflowListener::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(WorkflowListener entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName (entity.getName() + " 副本");
    }

    @Override
    public void enable(String id) {
        WorkflowListener entity=getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        WorkflowListener entity=getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

}

