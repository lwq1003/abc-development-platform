package tech.abc.platform.common.modules.system.api;


import tech.abc.platform.common.modules.system.params.LogDTO;

/**
 * @author: wqliu
 * @date 2020-03-26 19:31
 * : 日志服务
 */
public interface LogApi {
    /**
     * 添加日志
     *
     * @param logDTO
     */
    void add(LogDTO logDTO);
}
