package tech.abc.platform.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 图表统计 视图对象
 *
 * @author wqliu
 * @date 2025-11-28
 */
@Data
public class EchartVO {

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
