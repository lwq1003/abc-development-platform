package tech.abc.platform.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tech.abc.platform.common.constant.DateConstant;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.CommonUtil;
import tech.abc.platform.common.vo.SortInfo;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 查询生成器
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Component
@Slf4j
public class QueryGenerator {

    private static final String BEGIN = "BeginForQuery";
    private static final String END = "EndForQuery";
    private static final String STAR = "*";
    private static final String COMMA = ",";
    private static final String NOT_EQUAL = "!";


    /**
     * 构造查询条件构造器QueryWrapper实例
     */
    public static <E, VO> QueryWrapper<E> generateQueryWrapper(Class<E> entityClass, VO vo, SortInfo sortInfo) {

        QueryWrapper<E> queryWrapper = new QueryWrapper<E>();
        build(queryWrapper, entityClass, vo, sortInfo);
        return queryWrapper;
    }


    /**
     * 构造查询条件构造器QueryWrapper实例
     */
    public static <E, VO> QueryWrapper<E> generateQueryWrapper(Class<E> entityClass, VO vo) {
        return generateQueryWrapper(entityClass, vo, null);
    }

    /**
     * 构造查询对象
     */
    private static <E, VO> void build(QueryWrapper<E> queryWrapper, Class<E> entityClass, VO vo, SortInfo sortInfo) {

        // 获取实体属性
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(entityClass);
        // 遍历处理
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            Object value = null;
            try {
                value = PropertyUtils.getSimpleProperty(vo, name);
            } catch (Exception e) {
                // VO对象不一定包含Entity的每一个属性，此处找不到属于正常情况
            }
            // 单值处理
            if (value != null) {
                QueryRuleEnum rule = getRule(value);
                String valueString = value.toString();
                if (StringUtils.isNotBlank(valueString) && valueString.indexOf(')') >= 0) {
                    value = valueString.substring(valueString.indexOf(')') + 1);
                }

                addEasyQuery(queryWrapper, name, rule, value);
            }

        }
        // 起止范围处理，如日期、数值
        PropertyDescriptor[] voDescriptors = PropertyUtils.getPropertyDescriptors(vo.getClass());
        List<PropertyDescriptor> scopeList = Arrays.stream(voDescriptors)
                .filter(x -> x.getName().endsWith(BEGIN) || x.getName().endsWith(END)).collect(Collectors.toList());
        for (PropertyDescriptor field : scopeList) {
            String name = field.getName();
            Object value = null;
            try {
                Object scopeValue = PropertyUtils.getSimpleProperty(vo, name);
                if (name.endsWith(BEGIN)) {
                    addEasyQuery(queryWrapper, name.replace(BEGIN, ""), QueryRuleEnum.GE, scopeValue);

                } else {
                    // 结束类型如果为日期时间类型，且时间部分为00:00:00，即只传入日期，则业务查询期望包含当天数据,系统自动附加23:59:59
                    if (field.getPropertyType() == LocalDateTime.class) {
                        if (scopeValue != null) {
                            LocalDateTime endValue = (LocalDateTime) scopeValue;
                            if (endValue.format(DateTimeFormatter.ISO_TIME).equals(DateConstant.BEGIN_OF_DAY)) {
                                scopeValue =
                                        LocalDateTime.parse(endValue.format(DateTimeFormatter.ISO_DATE) + "T"
                                                + DateConstant.END_OF_DAY);
                            }
                        }

                    }
                    addEasyQuery(queryWrapper, name.replace(END, ""), QueryRuleEnum.LE, scopeValue);

                }
            } catch (Exception e) {
                log.error("获取对象属性出错", e);
                throw new CustomException(CommonException.PROPERTY_ACCESS_ERROR);

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


    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    private static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryRuleEnum rule, Object value) {

        if (value == null || rule == null || ObjectUtils.isEmpty(value)) {
            return;
        }
        name = CommonUtil.camelToUnderline(name);
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
            default:
                log.info("--查询规则未匹配到---");
                break;
        }
    }


    private static QueryRuleEnum getRule(Object value) {
        // 避免空数据
        if (value == null) {
            return null;
        }
        String val = (value + "").trim();
        if (val.length() == 0) {
            return null;
        }
        String patternString = "\\((.*?)\\)";
        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(val);
        if (matcher.find()) {
            String ruleString = matcher.group(1);
            return QueryRuleEnum.valueOf(ruleString);
        }
        // 对于数据字典，返回的是以逗号间隔的字符串，此种情况将操作符置为in
        if (StringUtils.contains(val, COMMA)) {
            return QueryRuleEnum.IN;
        }

        // 未找到，默认返回相等
        return QueryRuleEnum.EQ;

    }


}
