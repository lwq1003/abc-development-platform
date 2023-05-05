package tech.abc.platform.cip.service;

import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.cip.entity.ApiServiceLog;
import tech.abc.platform.common.base.BaseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * API服务日志 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface ApiServiceLogService extends BaseService<ApiServiceLog> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 记录日志
     *
     * @param request     请求
     * @param response    响应
     * @param receiveTime 接收时间
     */
    void recordLog(ApiRequest request, ApiResponse response, LocalDateTime receiveTime);


}

