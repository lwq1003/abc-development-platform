package tech.abc.platform.cip.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.ApiMessageStatusEnum;
import tech.abc.platform.cip.common.enums.MessageResponseResultEnum;
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
import java.util.stream.Collectors;

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
        entity.setSendCount(0);
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
    public void incrementSendCount(String messageId) {
        MessageLog apiMessageLog = getByRequestMessageId(messageId);
        apiMessageLog.setSendCount(apiMessageLog.getSendCount() + 1);
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
        // 发送次数设置为0
        log.setSendCount(0);
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
        log.setErrorMessage(message.getErrorMessage());
        log.setResponseId(message.getId());
        // 将消息更新为已响应
        log.setStatus(MessageStatusEnum.RESPONSED.name());

        modify(log);

    }

    @Override
    public List<String> getResendAppList(int maxSendCount) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeNow = now.minusSeconds(15);
        try {
            LambdaQueryChainWrapper<MessageLog> query = this.lambdaQuery()
                    // 消息状态为待发送或已发送
                    .and(x -> x.eq(MessageLog::getStatus, MessageStatusEnum.WAIT_REQUEST.name())
                            .or(y -> y.eq(MessageLog::getStatus, MessageStatusEnum.REQUESTED.name())))
                    // 排除掉登录请求
                    .ne(MessageLog::getRequestTopicCode, "framework.login.request")
                    // 发送方为消息服务中心
                    .eq(MessageLog::getRequestAppCode, appConfig.getMessage().getMessageServerAppCode())
                    // 发送次数小于设置的最大发送次数
                    .lt(MessageLog::getSendCount, maxSendCount)
                    // 请求时间小于当前时间15秒，避免刚产生的消息尚未收到服务端响应时就进行重发
                    .lt(MessageLog::getRequestTime, beforeNow)
                    // 按照响应应用编码分组
                    .groupBy(MessageLog::getResponseAppCode)
                    // 只查询应用编码
                    .select(MessageLog::getResponseAppCode);

            List<MessageLog> list = query.list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list.stream().map(x -> x.getResponseAppCode()).collect(Collectors.toList());
            } else {
                return null;
            }

        } catch (Exception e) {
            log.error("获取需要重发消息的应用列表出错", e);
            return null;
        }
    }

    @Override
    public List<MessageLog> getResendMessage(int messageCount, int maxSendCount, String appCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeNow = now.minusSeconds(15);
        try {
            LambdaQueryChainWrapper<MessageLog> query = this.lambdaQuery()
                    // 消息状态为待发送或已发送
                    .and(x -> x.eq(MessageLog::getStatus, MessageStatusEnum.WAIT_REQUEST.name())
                            .or(y -> y.eq(MessageLog::getStatus, MessageStatusEnum.REQUESTED.name())))
                    // 排除掉登录请求
                    .ne(MessageLog::getRequestTopicCode, "framework.login.request")
                    // 发送方为消息服务中心
                    .eq(MessageLog::getRequestAppCode, appConfig.getMessage().getMessageServerAppCode())
                    // 指定响应应用编码
                    .eq(MessageLog::getResponseAppCode, appCode)
                    // 发送次数小于设置的最大发送次数
                    .lt(MessageLog::getSendCount, maxSendCount)
                    // 请求时间小于当前时间15秒，避免刚产生的消息尚未收到服务端响应时就进行重发
                    .lt(MessageLog::getRequestTime, beforeNow)
                    // 按照请求时间升序排列
                    .orderByAsc(MessageLog::getRequestTime)
                    // 只取指定数量的消息
                    .last("limit " + messageCount);


            return query.list();
        } catch (Exception e) {
            log.error("获取发送信息失败", e);
            return null;
        }
    }

    @Override
    public List<MessageLog> queryWaitHandleMessages(int count, String appCode) {
        // 处理消息数量，如为0，默认设置默认数量，如超出最大数量，则置为最大数量
        if (count == 0) {
            count = DEFAULT_QUERY_MESSAGE_COUNT;
        } else if (count > MAX_QUERY_MESSAGE_COUNT) {
            count = MAX_QUERY_MESSAGE_COUNT;
        }

        List<MessageLog> list = this.lambdaQuery()
                .select(MessageLog::getRequestId, MessageLog::getRequestTopicCode, MessageLog::getRequestData, MessageLog::getRequestTime)
                .eq(MessageLog::getStatus, ApiMessageStatusEnum.WAIT_HANDLE.name())
                .eq(MessageLog::getResponseAppCode, appCode)
                .orderByAsc(MessageLog::getRequestTime)
                // 只取指定数量的消息
                .last("limit " + count)
                .list();
        return list;


    }

    @Override
    public void confirm(String requestMessageId, String appCode) {

        // 获取消息日志对象
        MessageLog log = getByRequestMessageId(requestMessageId);
        // 判断是否有权限对本消息确认
        if (!appCode.equals(log.getResponseAppCode())) {
            throw new CustomException(ApiMessageLogExceptionEnum.MESSAGE_CONFIRM_PERMISSION_ERROR);
        }

        // 更新消息日志
        log.setStatus(ApiMessageStatusEnum.HANDLED.name());
        log.setResponseResult(MessageResponseResultEnum.SUCCESS.name());
        log.setResponseTime(LocalDateTime.now());
        // 更新日志
        modify(log);

    }


}


