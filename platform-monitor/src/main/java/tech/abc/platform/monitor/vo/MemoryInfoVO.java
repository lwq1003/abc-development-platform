package tech.abc.platform.monitor.vo;

import lombok.Data;

/**
 * 内存信息
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class MemoryInfoVO {
    /**
     * 总内存(字节)
     */
    private Long total;

    /**
     * 可用内存(字节)
     */
    private Long available;

    /**
     * 已用内存(字节)
     */
    private Long used;

    /**
     * 内存使用率(%)
     */
    private Double usage;

    /**
     * 交换区总内存(字节)
     */
    private Long swapTotal;

    /**
     * 交换区已用内存(字节)
     */
    private Long swapUsed;

    /**
     * 交换区可用内存(字节)
     */
    private Long swapAvailable;

    /**
     * 交换区使用率(%)
     */
    private Double swapUsage;
}