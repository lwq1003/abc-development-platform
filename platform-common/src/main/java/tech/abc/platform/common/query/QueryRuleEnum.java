package tech.abc.platform.common.query;

import lombok.Getter;

/**
 * 查询规则
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum QueryRuleEnum {

    /**
     * 大于
     */
    GT,
    /**
     * 大于等于
     */
    GE,
    /**
     * 小于
     */
    LT,
    /**
     * 小于等于
     */
    LE,
    /**
     * 等于
     */
    EQ,
    /**
     * 不等于
     */
    NE,
    /**
     * 包含
     */
    IN,
    /**
     * 全模糊
     */
    LK,
    /**
     * 左模糊
     */
    LL,
    /**
     * 右模糊
     */
    RL,
    /**
     * 为空
     */
    NL,
    /**
     * 不为空
     */
    NN,
    /**
     * 自定义SQL片段
     */
    SQL_RULES;

}
