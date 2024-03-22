package tech.abc.platform.cip.service;

import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.entity.ActiveMessage;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 活跃消息 服务接口类
 *
 * @author wqliu
 * @date 2024-03-20
 */
public interface ActiveMessageService extends BaseService<ActiveMessage> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


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
    ActiveMessage getByRequestMessageId(String requestMessageId);

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
     * @param message 消息
     * @return {@link ActiveMessage}
     */
    ActiveMessage createRequestPart(RequestMessage message);

    /**
     * 创建日志，填充请求消息部分
     *
     * @param responseAppCode 响应应用编码
     * @param message         消息
     * @return {@link ActiveMessage}
     */
    ActiveMessage createRequestPart(RequestMessage message, String responseAppCode);

    /**
     * 更新日志，填充响应消息部分
     *
     * @param message
     */
    void updateResponsePart(ResponseMessage message);


    /**
     * 获取需要重发消息的应用列表
     *
     * @param maxSendCount 最大发送计数
     * @return {@link List}<{@link String}>
     */
    List<String> getResendAppList(int maxSendCount);

    /**
     * 获取重发消息
     *
     * @param messageCount 消息数量
     * @param maxSendCount 最大发送次数
     * @param appCode      应用编码
     * @return {@link List}<{@link ActiveMessage}>
     */
    List<ActiveMessage> getResendMessage(int messageCount, int maxSendCount, String appCode);


    /**
     * 查询等待处理的消息
     *
     * @param count   消息数量
     * @param appCode 应用编码
     * @return {@link List}<{@link ActiveMessage}>
     */
    List<ActiveMessage> queryWaitHandleMessages(int count, String appCode);


    /**
     * 消息确认
     *
     * @param requestMessageId 请求消息标识
     * @param appCode          应用程序代码
     */
    void confirm(String requestMessageId, String appCode);

    /**
     * 消息重发
     *
     * @param id
     */
    void resend(String id);
}

