package tech.abc.platform.cip.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.ApiMessageStatusEnum;
import tech.abc.platform.cip.common.enums.MessageResponseResultEnum;
import tech.abc.platform.cip.common.enums.MessageStatusEnum;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.cip.entity.ActiveMessage;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.exception.ActiveMessageExceptionEnum;
import tech.abc.platform.cip.mapper.ActiveMessageMapper;
import tech.abc.platform.cip.service.ActiveMessageService;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CustomException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活跃消息 服务实现类
 *
 * @author wqliu
 * @date 2024-03-20
 */
@Service
@Slf4j
public class ActiveMessageServiceImpl extends BaseServiceImpl<ActiveMessageMapper, ActiveMessage> implements ActiveMessageService {

    @Autowired
    private AppConfig appConfig;


    @Autowired
    private MessageLogService messageLogService;

    /**
     * 默认查询消息数量
     */
    private final int DEFAULT_QUERY_MESSAGE_COUNT = 5;
    /**
     * 最大查询消息数量
     */
    private final int MAX_QUERY_MESSAGE_COUNT = 20;

    @Override
    public ActiveMessage init() {
        ActiveMessage entity = new ActiveMessage();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        // 默认值处理
        entity.setResponseResult("");
        entity.setErrorMessage("");
        entity.setStatus("");
        entity.setSendCount(0);
        return entity;
    }

    @Override
    public void beforeAdd(ActiveMessage entity) {
        // 唯一性验证

    }

    @Override
    public void beforeModify(ActiveMessage entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ActiveMessage> list = this.lambdaQuery().in(ActiveMessage::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getRequestAppCode());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(ActiveMessage entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setRequestAppCode(entity.getRequestAppCode() + " 副本");
    }


    @Override
    public boolean checkRequestMessageExist(String requestMessageId) {
        long count = this.lambdaQuery().eq(ActiveMessage::getRequestId, requestMessageId).count();
        return count > 0;

    }

    @Override
    public boolean checkResponseMessageExist(String responseMessageId) {
        long count = this.lambdaQuery().eq(ActiveMessage::getResponseId, responseMessageId).count();
        return count > 0;
    }

    @Override
    public ActiveMessage getByRequestMessageId(String requestMessageId) {
        List<ActiveMessage> list = this.lambdaQuery().eq(ActiveMessage::getRequestId, requestMessageId).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            throw new CustomException(ActiveMessageExceptionEnum.ACTIVE_MESSAGE_NOT_EXIST);
        }
    }

    @Override
    public void updateStatus(String status, String messageId) {
        ActiveMessage apiActiveMessage = getByRequestMessageId(messageId);
        apiActiveMessage.setStatus(status);
        modify(apiActiveMessage);
    }

    @Override
    public void incrementSendCount(String messageId) {
        ActiveMessage apiActiveMessage = getByRequestMessageId(messageId);
        apiActiveMessage.setSendCount(apiActiveMessage.getSendCount() + 1);
        modify(apiActiveMessage);
    }

    @Override
    public ActiveMessage createRequestPart(RequestMessage message, String responseAppCode) {

        ActiveMessage log = new ActiveMessage();
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
    public ActiveMessage createRequestPart(RequestMessage message) {
        return createRequestPart(message, null);
    }

    @Override
    public void updateResponsePart(ResponseMessage message) {

        ActiveMessage log = getByRequestMessageId(message.getRequestMessageId());
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

    @Override
    public List<String> getResendAppList(int maxSendCount) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeNow = now.minusSeconds(15);
        try {
            LambdaQueryChainWrapper<ActiveMessage> query = this.lambdaQuery()
                    // 消息状态为待发送或已发送
                    .and(x -> x.eq(ActiveMessage::getStatus, MessageStatusEnum.WAIT_REQUEST.name())
                            .or(y -> y.eq(ActiveMessage::getStatus, MessageStatusEnum.REQUESTED.name())))
                    // 排除掉登录请求
                    .ne(ActiveMessage::getRequestTopicCode, "framework.login.request")
                    // 发送方为消息服务中心
                    .eq(ActiveMessage::getRequestAppCode, appConfig.getMessage().getMessageServerAppCode())
                    // 发送次数小于设置的最大发送次数
                    .lt(ActiveMessage::getSendCount, maxSendCount)
                    // 请求时间小于当前时间15秒，避免刚产生的消息尚未收到服务端响应时就进行重发
                    .lt(ActiveMessage::getRequestTime, beforeNow)
                    // 按照响应应用编码分组
                    .groupBy(ActiveMessage::getResponseAppCode)
                    // 只查询应用编码
                    .select(ActiveMessage::getResponseAppCode);

            List<ActiveMessage> list = query.list();
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
    public List<ActiveMessage> getResendMessage(int messageCount, int maxSendCount, String appCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeNow = now.minusSeconds(15);
        try {
            LambdaQueryChainWrapper<ActiveMessage> query = this.lambdaQuery()
                    // 消息状态为待发送或已发送
                    .and(x -> x.eq(ActiveMessage::getStatus, MessageStatusEnum.WAIT_REQUEST.name())
                            .or(y -> y.eq(ActiveMessage::getStatus, MessageStatusEnum.REQUESTED.name())))
                    // 排除掉登录请求
                    .ne(ActiveMessage::getRequestTopicCode, "framework.login.request")
                    // 发送方为消息服务中心
                    .eq(ActiveMessage::getRequestAppCode, appConfig.getMessage().getMessageServerAppCode())
                    // 指定响应应用编码
                    .eq(ActiveMessage::getResponseAppCode, appCode)
                    // 发送次数小于设置的最大发送次数
                    .lt(ActiveMessage::getSendCount, maxSendCount)
                    // 请求时间小于当前时间15秒，避免刚产生的消息尚未收到服务端响应时就进行重发
                    .lt(ActiveMessage::getRequestTime, beforeNow)
                    // 按照请求时间升序排列
                    .orderByAsc(ActiveMessage::getRequestTime)
                    // 只取指定数量的消息
                    .last("limit " + messageCount);


            return query.list();
        } catch (Exception e) {
            log.error("获取发送信息失败", e);
            return null;
        }
    }

    @Override
    public List<ActiveMessage> queryWaitHandleMessages(int count, String appCode) {
        // 处理消息数量，如为0，默认设置默认数量，如超出最大数量，则置为最大数量
        if (count == 0) {
            count = DEFAULT_QUERY_MESSAGE_COUNT;
        } else if (count > MAX_QUERY_MESSAGE_COUNT) {
            count = MAX_QUERY_MESSAGE_COUNT;
        }

        List<ActiveMessage> list = this.lambdaQuery()
                .select(ActiveMessage::getRequestId, ActiveMessage::getRequestTopicCode, ActiveMessage::getRequestData, ActiveMessage::getRequestTime)
                .eq(ActiveMessage::getStatus, ApiMessageStatusEnum.WAIT_HANDLE.name())
                .eq(ActiveMessage::getResponseAppCode, appCode)
                .orderByAsc(ActiveMessage::getRequestTime)
                // 只取指定数量的消息
                .last("limit " + count)
                .list();
        return list;
    }

    @Override
    public void confirm(String requestMessageId, String appCode) {

        // 获取消息日志对象
        ActiveMessage activeMessage = getByRequestMessageId(requestMessageId);
        // 判断是否有权限对本消息确认
        if (!appCode.equals(activeMessage.getResponseAppCode())) {
            throw new CustomException(ActiveMessageExceptionEnum.MESSAGE_CONFIRM_PERMISSION_ERROR);
        }

        // 更新消息日志
        activeMessage.setStatus(ApiMessageStatusEnum.HANDLED.name());
        activeMessage.setResponseResult(MessageResponseResultEnum.SUCCESS.name());
        activeMessage.setResponseTime(LocalDateTime.now());
        // 更新日志
        modify(activeMessage);

        // 拷贝至消息日志

        MessageLog messageLog = new MessageLog();
        BeanUtils.copyProperties(activeMessage, messageLog);
        messageLogService.add(messageLog);
        // 删除活跃消息
        remove(activeMessage.getId());


    }

    @Override
    public void resend(String id) {
        String[] idArray = StringUtils.split(id.toString(), ",");
        for (String item : idArray) {

            ActiveMessage entity = getEntity(item);
            if (entity.getStatus().equals(ApiMessageStatusEnum.WAIT_HANDLE.name())) {
                // 跳过处理通过API接口对接的待处理消息
                continue;
            }
            LambdaUpdateWrapper<ActiveMessage> lambdaUpdate = Wrappers.lambdaUpdate();
            lambdaUpdate.set(ActiveMessage::getSendCount, 0);
            lambdaUpdate.set(ActiveMessage::getErrorCode, null);
            lambdaUpdate.set(ActiveMessage::getErrorMessage, null);
            lambdaUpdate.set(ActiveMessage::getResponseId, null);
            lambdaUpdate.set(ActiveMessage::getResponseData, null);
            lambdaUpdate.set(ActiveMessage::getResponseTime, null);
            lambdaUpdate.set(ActiveMessage::getResponseTopicCode, null);
            lambdaUpdate.set(ActiveMessage::getResponseResult, null);
            lambdaUpdate.set(ActiveMessage::getStatus, MessageStatusEnum.WAIT_REQUEST.name());
            lambdaUpdate.set(ActiveMessage::getResponseTime, null);
            lambdaUpdate.eq(ActiveMessage::getId, entity.getId());
            update(entity, lambdaUpdate);
        }

    }


}

