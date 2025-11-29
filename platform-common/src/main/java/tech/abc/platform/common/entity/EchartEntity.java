package tech.abc.platform.common.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 图表实体
 *
 * @author wqliu
 * @date 2025-11-28
 */
@Data
public class EchartEntity {
    /**
     * 标签
     */
    private String label;
    /**
     * 数值
     */
    private BigDecimal valueDecimal;
    /**
     * 文本值
     */
    private String valueString;
}
