package tech.abc.platform.monitor.entity;

import lombok.Data;

/**
 * 操作系统信息实体类
 *
 * @author wqliu
 * @date 2025-12-11
 */
@Data
public class OsInfo {
    /**
     * 操作系统名称
     */
    private String name;

    /**
     * 操作系统版本
     */
    private String version;

    /**
     * 操作系统架构
     */
    private String arch;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 系统启动时间(毫秒)
     */
    private Long startTime;

    /**
     * 系统运行时长(毫秒)
     */
    private Long runningTime;
}
