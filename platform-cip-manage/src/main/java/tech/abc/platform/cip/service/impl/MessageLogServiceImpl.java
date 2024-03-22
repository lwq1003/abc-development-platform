package tech.abc.platform.cip.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.MessageStatusEnum;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.exception.ApiMessageLogExceptionEnum;
import tech.abc.platform.cip.mapper.MessageLogMapper;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CustomException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息日志 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class MessageLogServiceImpl extends BaseServiceImpl<MessageLogMapper, MessageLog> implements MessageLogService {
    @Autowired
    private AppConfig appConfig;


    /**
     * 默认查询消息数量
     */
    private final int DEFAULT_QUERY_MESSAGE_COUNT = 5;
    /**
     * 最大查询消息数量
     */
    private final int MAX_QUERY_MESSAGE_COUNT = 20;

    @Override
    public MessageLog init() {
        MessageLog entity = new MessageLog();
        // 默认值处理
        entity.setErrorMessage("");
        entity.setStatus("");
        return entity;
    }

    @Override
    public void beforeAdd(MessageLog entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(MessageLog entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<MessageLog> list = this.lambdaQuery().in(MessageLog::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getRequestAppCode());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(MessageLog entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setRequestAppCode(entity.getRequestAppCode() + " 副本");
    }

    @Override
    public boolean checkRequestMessageExist(String requestMessageId) {
        long count = this.lambdaQuery().eq(MessageLog::getRequestId, requestMessageId).count();
        return count > 0;

    }

    @Override
    public boolean checkResponseMessageExist(String responseMessageId) {
        long count = this.lambdaQuery().eq(MessageLog::getResponseId, responseMessageId).count();
        return count > 0;
    }

    @Override
    public MessageLog getByRequestMessageId(String requestMessageId) {
        List<MessageLog> list = this.lambdaQuery().eq(MessageLog::getRequestId, requestMessageId).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            throw new CustomException(ApiMessageLogExceptionEnum.MESSAGE_LOG_NOT_EXIST);
        }
    }

    @Override
    public void updateStatus(String status, String messageId) {
        MessageLog apiMessageLog = getByRequestMessageId(messageId);
        apiMessageLog.setStatus(status);
        modify(apiMessageLog);
    }


    @Override
    public MessageLog createRequestPart(RequestMessage message, String responseAppCode) {

        MessageLog log = new MessageLog();
        log.setRequestId(message.getId());
        log.setRequestAppCode(message.getPublishAppCode());
        log.setRequestTopicCode(message.getTopic());
        log.setRequestTime(LocalDateTime.now());
        log.setRequestData(message.getContent());
        log.setResponseAppCode(responseAppCode);
        add(log);
        return log;
    }

    @Override
    public MessageLog createRequestPart(RequestMessage message) {
        return createRequestPart(message, null);
    }

    @Override
    public void updateResponsePart(ResponseMessage message) {

        MessageLog log = getByRequestMessageId(message.getRequestMessageId());
        // 响应消息的发布者，对应请求消息的响应
        log.setResponseAppCode(message.getPublishAppCode());
        log.setResponseTopicCode(message.getTopic());
        log.setResponseTime(LocalDateTime.now());
        log.setResponseData(message.getContent());
        log.setResponseResult(message.getResult());
        log.setErrorCode(message.getErrorCode());
        log.setErrorMessage(message.getErrorMessage());
        log.setResponseId(message.getId());
        // 将消息更新为已响应
        log.setStatus(MessageStatusEnum.RESPONSED.name());

        modify(log);

    }

}


