package tech.abc.platform.system.service;

/**
 * 系统管理服务
 *
 * @author wqliu
 * @date 2023-03-08
 */
public interface SystemManageService {
    /**
     * 重建系统缓存
     */
    void rebuildSystemCache();


    /**
     * 获取唯一标识
     * @return {@link String}
     */
    String getUniqueId();
}
