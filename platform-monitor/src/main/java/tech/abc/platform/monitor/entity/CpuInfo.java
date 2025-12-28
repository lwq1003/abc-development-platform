package tech.abc.platform.monitor.entity;

import lombok.Data;

import java.util.List;

/**
 * CPU信息实体类
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class CpuInfo {
    /**
     * 处理器名称
     */
    private String name;

    /**
     * 处理器型号
     */
    private String model;

    /**
     * 物理核心数
     */
    private Integer physicalProcessorCount;

    /**
     * 逻辑核心数
     */
    private Integer logicalProcessorCount;

    /**
     * CPU使用率(%)
     */
    private Double usage;

    /**
     * 各核心CPU使用率(%)
     */
    private List<Double> coreUsages;
}
