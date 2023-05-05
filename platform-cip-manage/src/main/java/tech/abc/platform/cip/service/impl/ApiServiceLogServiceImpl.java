package tech.abc.platform.cip.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.cip.entity.ApiServiceLog;
import tech.abc.platform.cip.mapper.ApiServiceLogMapper;
import tech.abc.platform.cip.service.ApiServiceLogService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.utils.DateUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API服务日志 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class ApiServiceLogServiceImpl extends BaseServiceImpl<ApiServiceLogMapper, ApiServiceLog> implements ApiServiceLogService {
    @Override
    public ApiServiceLog init() {
        ApiServiceLog entity = new ApiServiceLog();
        // 默认值处理
        entity.setErrorMessage("");
        return entity;
    }

    @Override
    public void beforeAdd(ApiServiceLog entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(ApiServiceLog entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ApiServiceLog> list = this.lambdaQuery().in(ApiServiceLog::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getAppCode());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(ApiServiceLog entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setAppCode(entity.getAppCode() + " 副本");
    }

    @Override
    public void recordLog(ApiRequest request, ApiResponse response, LocalDateTime receiveTime) {

        LocalDateTime responseTime = LocalDateTime.now();
        ApiServiceLog apiServiceLog = new ApiServiceLog();
        apiServiceLog.setAppCode(request.getAppCode());
        apiServiceLog.setServiceCode(request.getServiceCode());
        apiServiceLog.setRequestTime(DateUtils.toLocalDateTime(request.getRequestTime()));
        apiServiceLog.setReceiveTime(receiveTime);
        apiServiceLog.setRequestBusinessData(request.getData());
        apiServiceLog.setExecuteResult(response.getExecuteResult());
        apiServiceLog.setErrorCode(response.getErrorCode());
        apiServiceLog.setErrorMessage(response.getErrorMessage());
        apiServiceLog.setResponseTime(responseTime);
        apiServiceLog.setResponseBusinessData(response.getData());
        // 计算执行时间
        apiServiceLog.setExecutionTime(Duration.between(receiveTime, responseTime).toMillis());
        // 保存
        add(apiServiceLog);

    }
}


