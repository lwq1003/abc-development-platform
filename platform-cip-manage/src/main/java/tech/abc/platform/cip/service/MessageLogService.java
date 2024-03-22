package tech.abc.platform.cip.service;

import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 消息日志 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface MessageLogService extends BaseService<MessageLog> {

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
    MessageLog getByRequestMessageId(String requestMessageId);

    /**
     * 更新状态
     *
     * @param status    状态
     * @param messageId 消息标识
     */
    void updateStatus(String status, String messageId);


    /**
     * 创建日志，填充请求消息部分
     *
     * @param message 消息
     * @return {@link MessageLog}
     */
    MessageLog createRequestPart(RequestMessage message);

    /**
     * 创建日志，填充请求消息部分
     *
     * @param responseAppCode 响应应用编码
     * @param message         消息
     * @return {@link MessageLog}
     */
    MessageLog createRequestPart(RequestMessage message, String responseAppCode);

    /**
     * 更新日志，填充响应消息部分
     *
     * @param message
     */
    void updateResponsePart(ResponseMessage message);


}


