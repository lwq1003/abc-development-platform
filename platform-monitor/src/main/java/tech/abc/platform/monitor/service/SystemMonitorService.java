package tech.abc.platform.monitor.service;

import tech.abc.platform.monitor.entity.SystemMonitor;

/**
 * 系统监控服务
 *
 * @author wqliu
 * @date 2025-12-11
 */
public interface SystemMonitorService {
    /**
     * 获取系统监控信息
     *
     * @return 系统监控信息
     */
    SystemMonitor getSystemMonitorInfo();
}