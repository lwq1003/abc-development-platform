package tech.popsoft.cip.client.manage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.MessageStatusEnum;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CustomException;
import tech.popsoft.cip.client.manage.entity.ApiMessageLog;
import tech.popsoft.cip.client.manage.exception.ApiMessageLogExceptionEnum;
import tech.popsoft.cip.client.manage.mapper.ApiMessageLogMapper;
import tech.popsoft.cip.client.manage.service.ApiMessageLogService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息日志 服务实现类
 *
 * @author wqliu
 * @date 2021-08-21
 */
@Service
@Slf4j
public class ApiMessageLogServiceImpl extends BaseServiceImpl<ApiMessageLogMapper, ApiMessageLog> implements ApiMessageLogService {
    @Override
    public ApiMessageLog init() {
        ApiMessageLog entity = new ApiMessageLog();

        return entity;
    }

    @Override
    public boolean checkRequestMessageExist(String requestMessageId) {
        long count = this.lambdaQuery().eq(ApiMessageLog::getRequestId, requestMessageId).count();
        return count > 0;

    }

    @Override
    public boolean checkResponseMessageExist(String responseMessageId) {
        long count = this.lambdaQuery().eq(ApiMessageLog::getResponseId, responseMessageId).count();
        return count > 0;
    }

    @Override
    public ApiMessageLog getByRequestMessageId(String requestMessageId) {
        List<ApiMessageLog> list = this.lambdaQuery().eq(ApiMessageLog::getRequestId, requestMessageId).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);

        } else {
            throw new CustomException(ApiMessageLogExceptionEnum.MESSAGE_LOG_NOT_EXIST);
        }
    }

    @Override
    public void updateStatus(String status, String messageId) {
        ApiMessageLog apiMessageLog = getByRequestMessageId(messageId);
        apiMessageLog.setStatus(status);
        modify(apiMessageLog);
    }


    @Override
    public ApiMessageLog createRequestPart(RequestMessage message, String responseAppCode) {

        ApiMessageLog log = new ApiMessageLog();
        log.setRequestId(message.getId());
        log.setRequestAppCode(message.getPublishAppCode());
        log.setRequestTopicCode(message.getTopic());
        log.setRequestTime(LocalDateTime.now());
        log.setRequestData(message.getContent());
        log.setResponseAppCode(responseAppCode);
        // 发送次数设置为0
        log.setSendCount(0);
        add(log);
        return log;

    }

    @Override
    public ApiMessageLog createRequestPart(RequestMessage message) {
        return createRequestPart(message, null);
    }

    @Override
    public void updateResponsePart(ResponseMessage message) {

        ApiMessageLog log = getByRequestMessageId(message.getRequestMessageId());
        // 响应消息的发布者，对应请求消息的响应
        log.setResponseAppCode(message.getPublishAppCode());
        log.setResponseTopicCode(message.getTopic());
        log.setResponseTime(LocalDateTime.now());
        log.setResponseData(message.getContent());
        log.setResponseResult(message.getResult());
        log.setErrorMessage(message.getErrorMessage());
        log.setResponseId(message.getId());
        // 将消息更新为已响应
        log.setStatus(MessageStatusEnum.RESPONSED.name());

        modify(log);

    }

    @Override
    public List<ApiMessageLog> getResendMessage(int messageCount, int maxSendCount) {
        LocalDateTime now = LocalDateTime.now();
        // 获取当前时间之前15秒的时间点
        LocalDateTime beforeNow = now.minusSeconds(15);
        try {
            LambdaQueryChainWrapper<ApiMessageLog> query = this.lambdaQuery()
                    // 消息状态为待发送或已发送
                    .and(x -> x.eq(ApiMessageLog::getStatus, MessageStatusEnum.WAIT_REQUEST.name())
                            .or(y -> y.eq(ApiMessageLog::getStatus, MessageStatusEnum.REQUESTED.name())))
                    // 排除掉登录请求
                    .ne(ApiMessageLog::getRequestTopicCode, "framework.login.request")
                    // 发送次数小于设置的最大发送次数
                    .lt(ApiMessageLog::getSendCount, maxSendCount)
                    // 请求时间小于当前时间15秒，避免刚产生的消息尚未收到服务端响应时就进行重发
                    .lt(ApiMessageLog::getRequestTime, beforeNow)
                    // 按照请求时间升序排列
                    .orderByAsc(ApiMessageLog::getRequestTime)
                    // 只取指定数量的消息
                    .last("limit " + messageCount);


            return query.list();
        } catch (Exception e) {
            log.error("获取发送信息失败", e);
            return null;
        }

    }

    @Override
    public void incrementSendCount(String messageId) {
        ApiMessageLog apiMessageLog = getByRequestMessageId(messageId);
        apiMessageLog.setSendCount(apiMessageLog.getSendCount() + 1);
        modify(apiMessageLog);
    }
}
