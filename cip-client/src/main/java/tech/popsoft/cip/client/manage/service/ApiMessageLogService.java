package tech.popsoft.cip.client.manage.service;


import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.common.base.BaseService;
import tech.popsoft.cip.client.manage.entity.ApiMessageLog;

import java.util.List;


/**
 * 消息日志 服务类
 *
 * @author wqliu
 * @date 2021-08-21
 */
public interface ApiMessageLogService extends BaseService<ApiMessageLog> {

    /**
     * 检查请求消息是否已存在
     *
     * @param requestMessageId 请求消息标识
     * @return true  存在  false  不存在
     */
    boolean checkRequestMessageExist(String requestMessageId);

    /**
     * 检查响应消息是否已存在
     *
     * @param responseMessageId 响应消息标识
     * @return true  存在  false  不存在
     */
    boolean checkResponseMessageExist(String responseMessageId);

    /**
     * 根据请求消息标识获取消息日志对象
     *
     * @param requestMessageId 请求消息标识
     * @return 消息日志对象
     */
    ApiMessageLog getByRequestMessageId(String requestMessageId);

    /**
     * 更新状态
     *
     * @param status    状态
     * @param messageId 消息标识
     */
    void updateStatus(String status, String messageId);

    /**
     * 递增发送计数
     *
     * @param messageId 消息标识
     */
    void incrementSendCount(String messageId);


    /**
     * 创建日志，填充请求消息部分
     *
     * @param message
     */
    ApiMessageLog createRequestPart(RequestMessage message);

    /**
     * 创建日志，填充请求消息部分
     *
     * @param message
     * @param responseAppCode 响应应用编码
     */
    ApiMessageLog createRequestPart(RequestMessage message, String responseAppCode);


    /**
     * 更新日志，填充响应消息部分
     *
     * @param message
     */
    void updateResponsePart(ResponseMessage message);


    /**
     * 获取重发消息
     *
     * @param messageCount 消息数量
     * @param maxSendCount 最大发送次数
     * @return {@link List}<{@link ApiMessageLog}>
     */
    List<ApiMessageLog> getResendMessage(int messageCount, int maxSendCount);
}
