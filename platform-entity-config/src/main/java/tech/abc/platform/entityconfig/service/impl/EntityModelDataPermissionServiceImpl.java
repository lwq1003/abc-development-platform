package tech.abc.platform.entityconfig.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.query.QueryRuleEnum;
import tech.abc.platform.common.utils.CommonUtil;
import tech.abc.platform.entityconfig.entity.Entity;
import tech.abc.platform.entityconfig.entity.EntityModel;
import tech.abc.platform.entityconfig.entity.EntityModelDataPermission;
import tech.abc.platform.entityconfig.entity.EntityModelProperty;
import tech.abc.platform.entityconfig.mapper.EntityModelDataPermissionMapper;
import tech.abc.platform.entityconfig.service.EntityModelDataPermissionService;
import tech.abc.platform.entityconfig.service.EntityModelPropertyService;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.entityconfig.service.EntityService;
import tech.abc.platform.entityconfig.vo.DataFilterConditionVO;
import tech.abc.platform.entityconfig.vo.DataFilterGroupVO;
import tech.abc.platform.entityconfig.vo.DataFilterRuleVO;
import tech.abc.platform.system.entity.Module;
import tech.abc.platform.system.service.ModuleService;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 实体模型数据权限 服务实现类
 *
 * @author wqliu
 * @date 2024-08-03
 */
@Service
@Slf4j
public class EntityModelDataPermissionServiceImpl extends BaseServiceImpl<EntityModelDataPermissionMapper, EntityModelDataPermission> implements EntityModelDataPermissionService {

    @Resource
    private EntityModelService entityModelService;

    @Resource
    private EntityService entityService;

    @Resource
    private ModuleService moduleService;

    @Resource
    private EntityModelPropertyService entityModelPropertyService;


    /**
     * 逗号
     */
    private static final String COMMA = ",";

    @Override
    public EntityModelDataPermission init() {
        EntityModelDataPermission entity = new EntityModelDataPermission();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(EntityModelDataPermission entity) {
        // 唯一性验证

    }

    @Override
    public void beforeModify(EntityModelDataPermission entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<EntityModelDataPermission> list = this.lambdaQuery().in(EntityModelDataPermission::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getTableName());
                });
            }
        }
        return result;
    }

    @Override
    public EntityModelDataPermission getOrInit(String modelId) {
        List<EntityModelDataPermission> list = this.lambdaQuery().eq(EntityModelDataPermission::getModelId, modelId).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            EntityModelDataPermission entity = this.init();
            entity.setModelId(modelId);
            entity.setTableName(generateTableName(modelId));
            add(entity);
            return entity;
        }


    }

    @Override
    public String getDataPermissionSqlPart(String tableName) {
        Optional<EntityModelDataPermission> entityModelDataPermission = this.lambdaQuery()
                .eq(EntityModelDataPermission::getTableName, tableName).oneOpt();
        if (entityModelDataPermission.isPresent()) {
            return entityModelDataPermission.get().getSqlPart();
        } else {
            return null;
        }

    }

    @Override
    public String generateSqlPart(String id, String rule) {
        String result = "";

        List<EntityModelProperty> modelPropertyList = entityModelPropertyService.getFullPropertyByEntityModelId(id);
        String[] numberDataType = {"INTEGER", "LONG", "DOUBLE", "DECIMAL"};

        // 转换数据
        DataFilterRuleVO dataFilterRule = JSON.parseObject(rule, DataFilterRuleVO.class);
        // 获取组集合
        List<DataFilterGroupVO> dataFilterGroupList = dataFilterRule.getFilters();
        // 存放各组对应的条件sql片段
        List<String> groupSqlPartList = new ArrayList<>(dataFilterGroupList.size());
        // 遍历组集合
        for (DataFilterGroupVO dataFilterGroup : dataFilterGroupList) {
            // 获取条件集合
            List<DataFilterConditionVO> conditionList = dataFilterGroup.getConditions();
            if (CollectionUtils.isNotEmpty(conditionList)) {
                // 存放各条件对应的条件sql片段
                List<String> conditionSqlPartList = new ArrayList<>(conditionList.size());
                // 遍历条件集合
                for (DataFilterConditionVO condition : conditionList) {
                    // 获取字段名,命名风格驼峰转换成下划线
                    String fieldName = condition.getProperty();
                    Object value = condition.getValue();
                    // 获取操作
                    String operator = condition.getOperator();
                    QueryRuleEnum queryRule = EnumUtils.getEnum(QueryRuleEnum.class, operator, QueryRuleEnum.EQ);
                    QueryWrapper<?> queryWrapper = new QueryWrapper<>();
                    addEasyQuery(queryWrapper, fieldName, queryRule, value);

                    log.info(queryWrapper.getSqlSegment());
                    log.info(queryWrapper.getParamNameValuePairs().toString());
                    AtomicBoolean numberFlag = new AtomicBoolean(false);
                    modelPropertyList.stream().filter(modelProperty -> modelProperty.getCode().equals(condition.getProperty())).findFirst()
                            .ifPresent(modelProperty -> {
                                String dataType = modelProperty.getDataType();
                                if (ArrayUtils.contains(numberDataType, dataType)) {
                                    numberFlag.set(true);
                                }

                            });


                    String sqlPart = handleSqlPart(queryWrapper.getSqlSegment(), queryWrapper.getParamNameValuePairs(), numberFlag.get());
                    // 放入结果列表缓存
                    conditionSqlPartList.add(sqlPart);
                }
                // 完成各条件的处理后，进行组内拼接
                if (conditionList.size() > 1) {
                    String logicalOperator = dataFilterGroup.getLogicalOperator();
                    StringBuffer sb = new StringBuffer();
                    sb.append("(");
                    conditionSqlPartList.stream().forEach(sqlPart -> {
                        sb.append(sqlPart);
                        sb.append(" " + logicalOperator + " ");
                    });
                    sb.delete(sb.length() - logicalOperator.length() - 1, sb.length());
                    sb.append(")");
                    groupSqlPartList.add(sb.toString());

                } else {
                    groupSqlPartList.add(conditionSqlPartList.get(0));

                }
            }

        }


        // 完成各条件组的处理后，进行组间拼接
        if (groupSqlPartList.size() > 1) {
            String logicalOperator = dataFilterRule.getLogicalOperator();
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            groupSqlPartList.stream().forEach(sqlPart -> {
                sb.append(sqlPart);
                sb.append(" " + logicalOperator + " ");
            });
            sb.delete(sb.length() - logicalOperator.length() - 1, sb.length());
            sb.append(")");
            result = sb.toString();

        } else {
            result = groupSqlPartList.get(0);

        }

        return result;

    }

    /**
     * 将参数化的SQL填充值处理为完整的SQL片段
     *
     * @param sqlSegment          参数化的SQL片段
     * @param paramNameValuePairs 参数值对
     * @param numberFlag          是否为数值类型
     * @return 完整的SQL片段
     */
    private String handleSqlPart(String sqlSegment, Map<String, Object> paramNameValuePairs, boolean numberFlag) {

        for (int i = 0; i < paramNameValuePairs.size(); i++) {

            String value = paramNameValuePairs.get("MPGENVAL" + (i + 1)).toString();
            // 若为非数值类，则加上单引号
            if (!numberFlag) {
                value = "'" + value + "'";
            }
            sqlSegment = StringUtils.replace(sqlSegment, "#{ew.paramNameValuePairs.MPGENVAL" + (i + 1) + "}", value);

        }
        return sqlSegment;
    }


    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param origName     字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    private void addEasyQuery(QueryWrapper<?> queryWrapper, String origName, QueryRuleEnum rule, Object value) {

        if (value == null || rule == null || ObjectUtils.isEmpty(value)) {
            return;
        }
        String name = CommonUtil.camelToUnderline(origName);
        switch (rule) {
            case GT:
                queryWrapper.gt(name, value);
                break;
            case GE:
                queryWrapper.ge(name, value);
                break;
            case LT:
                queryWrapper.lt(name, value);
                break;
            case LE:
                queryWrapper.le(name, value);
                break;
            case EQ:
                queryWrapper.eq(name, value);
                break;
            case NE:
                queryWrapper.ne(name, value);
                break;
            case IN:
                if (value instanceof String) {
                    queryWrapper.in(name, (Object[]) value.toString().split(COMMA));
                } else if (value instanceof String[]) {
                    queryWrapper.in(name, (Object[]) value);
                } else if (value instanceof JSONArray) {
                    queryWrapper.in(name, ((JSONArray) value).toArray());
                } else {
                    queryWrapper.in(name, value);
                }
                break;
            case LK:
                queryWrapper.like(name, value);
                break;
            case LL:
                queryWrapper.likeLeft(name, value);
                break;
            case RL:
                queryWrapper.likeRight(name, value);
                break;
            case CT:
                List<String> list = new ArrayList<>();
                list.add(origName);
                queryWrapper.in("'" + value.toString() + "'", list);
                break;
            default:
                log.info("--查询规则未匹配到---");
                break;
        }
    }


    private String generateTableName(String modelId) {
        // 获取模型编码
        EntityModel entityModel = entityModelService.query(modelId);
        String entityCode = entityModel.getCode();
        // 获取模块编码缩写
        Entity entity = entityService.query(entityModel.getEntity());
        Module module = moduleService.query(entity.getModule());
        String moduleAbbreviation = module.getAbbreviation();
        // 转换拼装为表名
        return moduleAbbreviation.toLowerCase() + "_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityCode);
    }

    @Override
    protected void copyPropertyHandle(EntityModelDataPermission entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setTableName(entity.getTableName() + " 副本");
    }

}

