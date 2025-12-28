package tech.abc.platform.monitor.entity;

import lombok.Data;

/**
 * 系统监控信息实体类
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class SystemMonitor {
    /**
     * 操作系统信息
     */
    private OsInfo osInfo;

    /**
     * CPU信息
     */
    private CpuInfo cpuInfo;

    /**
     * 内存信息
     */
    private MemoryInfo memoryInfo;

    /**
     * 磁盘信息
     */
    private DiskInfo diskInfo;

    /**
     * JVM信息
     */
    private JvmInfo jvmInfo;
}
