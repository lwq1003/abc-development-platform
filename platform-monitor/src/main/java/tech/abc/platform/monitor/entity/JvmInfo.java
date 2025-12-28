package tech.abc.platform.monitor.entity;

import lombok.Data;

/**
 * JVM信息实体类
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class JvmInfo {
    /**
     * JVM名称
     */
    private String name;

    /**
     * JVM版本
     */
    private String version;

    /**
     * JVM供应商
     */
    private String vendor;

    /**
     * JVM启动时间(毫秒)
     */
    private Long startTime;

    /**
     * JVM运行时长(毫秒)
     */
    private Long runningTime;

    /**
     * 最大堆内存(字节)
     */
    private Long maxHeapMemory;

    /**
     * 已用堆内存(字节)
     */
    private Long usedHeapMemory;

    /**
     * 已提交堆内存(字节)
     */
    private Long committedHeapMemory;

    /**
     * 初始堆内存(字节)
     */
    private Long initHeapMemory;

    /**
     * 非堆内存总量(字节)
     */
    private Long totalNonHeapMemory;

    /**
     * 已用非堆内存(字节)
     */
    private Long usedNonHeapMemory;

    /**
     * 已提交非堆内存(字节)
     */
    private Long committedNonHeapMemory;

    /**
     * 初始非堆内存(字节)
     */
    private Long initNonHeapMemory;

    /**
     * 线程数量
     */
    private Integer threadCount;

    /**
     * 守护线程数量
     */
    private Integer daemonThreadCount;

    /**
     * 峰值线程数量
     */
    private Integer peakThreadCount;

    /**
     * 类加载数量
     */
    private Long loadedClassCount;

    /**
     * 未加载类数量
     */
    private Long unloadedClassCount;

    /**
     * 总类加载数量
     */
    private Long totalLoadedClassCount;
}
