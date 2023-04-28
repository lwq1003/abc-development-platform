package tech.abc.platform.common.query;

import lombok.Data;

/**
 * 查询条件
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class QueryCondition {

    /**
     * 字段名
     */
    private String field;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 规则类型
     */
    private String rule;
    /**
     * 值
     */
    private String value;
}
