package tech.abc.platform.common.query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tech.abc.platform.common.utils.CommonUtil;
import tech.abc.platform.common.vo.DataFilterConditionVO;
import tech.abc.platform.common.vo.DataFilterGroupVO;
import tech.abc.platform.common.vo.DataFilterRuleVO;
import tech.abc.platform.common.vo.SortInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义查询生成器
 *
 * @author wqliu
 * @date 2024-09-03
 */
@Component
@Slf4j
public class CustomQueryGenerator {


    /**
     * 逗号
     */
    private static final String COMMA = ",";
    /**
     * 或
     */
    public static final String OR = "or";

    /**
     * 与
     */
    public static final String AND = "and";

    /**
     * 构造查询条件构造器QueryWrapper实例
     */
    public static <E, VO> QueryWrapper<E> generateQueryWrapper(Class<E> entityClass, String customQueryString, SortInfo sortInfo) {

        QueryWrapper<E> queryWrapper = new QueryWrapper<E>();
        build(queryWrapper, entityClass, customQueryString, sortInfo);
        return queryWrapper;
    }

    private static <E, VO> void build(QueryWrapper<E> queryWrapper, Class<E> entityClass, String customQueryString, SortInfo sortInfo) {
        // 转换数据
        DataFilterRuleVO dataFilterRule = JSON.parseObject(customQueryString, DataFilterRuleVO.class);
        // 获取组集合
        List<DataFilterGroupVO> dataFilterGroupList = dataFilterRule.getFilters();
        // 组间条件逻辑关系
        String logicalOperatorGroup = dataFilterRule.getLogicalOperator();
        // 遍历组集合
        for (DataFilterGroupVO dataFilterGroup : dataFilterGroupList) {
            if (logicalOperatorGroup.equals(OR)) {
                queryWrapper.or(x ->
                        generateQueryWrapper(x, dataFilterGroup)
                );
            } else {
                queryWrapper.and(x ->
                        generateQueryWrapper(x, dataFilterGroup)
                );
            }
        }
        // 附加排序
        if (sortInfo != null && StringUtils.isNotBlank(sortInfo.getField())) {
            // 此处未使用注解，而是按照约定的规则，将驼峰命名转换为下划线，从而获取到数据库表字段名
            String orderField = CommonUtil.camelToUnderline(sortInfo.getField());
            if (sortInfo.getAscType()) {
                queryWrapper.orderByAsc(orderField);
            } else {
                queryWrapper.orderByDesc(orderField);
            }
        }
    }

    private static <E> QueryWrapper<E> generateQueryWrapper(QueryWrapper<E> queryWrapper, DataFilterGroupVO dataFilterGroup) {
        // 获取条件集合
        List<DataFilterConditionVO> conditionList = dataFilterGroup.getConditions();
        // 组内条件逻辑关系
        String logicalOperator = dataFilterGroup.getLogicalOperator();
        if (CollectionUtils.isNotEmpty(conditionList)) {

            // 遍历条件集合
            for (DataFilterConditionVO condition : conditionList) {
                if (logicalOperator.equals(OR)) {
                    queryWrapper = queryWrapper.or();
                }
                // 获取字段名,命名风格驼峰转换成下划线
                String fieldName = condition.getProperty();
                Object value = condition.getValue();
                // 获取操作
                String operator = condition.getOperator();
                QueryRuleEnum queryRule = EnumUtils.getEnum(QueryRuleEnum.class, operator, QueryRuleEnum.EQ);

                addEasyQuery(queryWrapper, fieldName, queryRule, value);
            }
        }
        return queryWrapper;
    }

    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param origName     字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    private static void addEasyQuery(QueryWrapper<?> queryWrapper, String origName, QueryRuleEnum rule, Object value) {

        // 规则为空，直接退出
        if (rule == null) {
            return;
        }
        // 值为空，且操作符不为“为空”和“不为空”，直接退出
        if (ObjectUtils.isEmpty(value)) {
            if (rule != QueryRuleEnum.NL && rule != QueryRuleEnum.NN) {
                return;
            }
        }

        // 处理日期类型
        if (value instanceof JSONObject) {
            long timestamp = Long.parseLong(((JSONObject) value).getString("value"));
            value = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
            log.info(value.toString());

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
            case NN:
                queryWrapper.isNotNull(name);
                break;
            case NL:
                queryWrapper.isNull(name);
                break;
            default:
                log.info("--查询规则未匹配到---");
                break;
        }
    }


}
