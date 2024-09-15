package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.QueryPlan;
import tech.abc.platform.system.mapper.QueryPlanMapper;
import tech.abc.platform.system.service.QueryPlanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询方案 服务实现类
 *
 * @author wqliu
 * @date 2024-09-04
 */
@Service
@Slf4j
public class QueryPlanServiceImpl extends BaseServiceImpl<QueryPlanMapper, QueryPlan> implements QueryPlanService {

    @Override
    public QueryPlan init() {
        QueryPlan entity = new QueryPlan();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(QueryPlan entity) {
        // 唯一性验证
        // 验证 名称 同一用户同一实体模型下唯一
        long countName = this.lambdaQuery().eq(QueryPlan::getName, entity.getName())
                .eq(QueryPlan::getUserId, entity.getUserId())
                .eq(QueryPlan::getEntityModelCode, entity.getEntityModelCode())
                .count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
    }

    @Override
    protected void beforeAddOrModifyOp(QueryPlan entity) {
        entity.setUserId(UserUtil.getId());
    }

    @Override
    public void beforeModify(QueryPlan entity) {
        // 唯一性验证
        // 唯一性验证
        // 验证 名称 同一用户同一实体模型下唯一
        long countName = this.lambdaQuery().eq(QueryPlan::getName, entity.getName())
                .eq(QueryPlan::getUserId, entity.getUserId())
                .eq(QueryPlan::getEntityModelCode, entity.getEntityModelCode())
                .ne(QueryPlan::getId, entity.getId())
                .count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<QueryPlan> list = this.lambdaQuery().in(QueryPlan::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(QueryPlan entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

}

