package tech.abc.platform.monitor.vo;

import lombok.Data;

/**
 * 系统监控信息
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class SystemMonitorVO {
    /**
     * 操作系统信息
     */
    private OsInfoVO osInfo;

    /**
     * CPU信息
     */
    private CpuInfoVO cpuInfo;

    /**
     * 内存信息
     */
    private MemoryInfoVO memoryInfo;

    /**
     * 磁盘信息
     */
    private DiskInfoVO diskInfo;

    /**
     * JVM信息
     */
    private JvmInfoVO jvmInfo;
}