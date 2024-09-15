package tech.abc.platform.common.vo;

import lombok.Data;

/**
 * 数据筛选条件
 *
 * @author wqliu
 * @date 2024-08-06
 */
@Data
public class DataFilterConditionVO {

    /**
     * 属性
     */
    private String property;

    /**
     * 操作
     */
    private String operator;

    /**
     * 值
     */
    private Object value;

}
