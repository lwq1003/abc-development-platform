package tech.abc.platform.cip.message.framework.sender;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.enums.MessageStatusEnum;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.message.framework.MessageServerHolder;
import tech.abc.platform.cip.service.AppDataPermissionService;
import tech.abc.platform.cip.service.MessageSubscriptionService;
import tech.abc.platform.common.utils.SpringUtil;

import java.util.List;


/**
 * 请求消息发送器
 *
 * @author wqliu
 * @date 2021-10-5
 */
@Slf4j
public class RequestMessageSender extends MessageSender {


    protected MessageSubscriptionService apiMessageSubscriptionService = SpringUtil.getBean(MessageSubscriptionService.class);

    protected AppDataPermissionService apiDataPermissionService = SpringUtil.getBean(AppDataPermissionService.class);

    /**
     * 数据权限通配符
     */
    public static final String DATA_PERMISSION_ALL = "*";


    public RequestMessageSender(String topic) {
        super(topic);

    }

    /**
     * 发送消息
     *
     * @param appCode 应用标识
     * @param content 消息内容
     * @param id      消息标识
     */
    public void sendMessage(String appCode, String content, String id) {

        // 生成请求消息
        RequestMessage message = new RequestMessage();
        // 使用已有ID重置默认生成的ID，用于关联消息
        if (StringUtils.isNotBlank(id)) {
            message.setId(id);
        }
        // 设置相关属性
        message.setTopic(super.getTopic());
        // 参数中消息内容优先，如为空，取对象属性的值
        if (StringUtils.isNotBlank(content)) {
            message.setContent(content);
        } else {
            message.setContent(this.getContent());
        }
        message.setPublishAppCode(appConfig.getMessage().getMessageServerAppCode());

        // 获取发送通道
        Channel channel = MessageServerHolder.getChannel(appCode);
        if (channel != null && channel.isActive()) {
            ChannelFuture channelFuture = channel.writeAndFlush(message);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                    if (StringUtils.isBlank(id)) {
                        // 创建日志
                        MessageLog log = apiMessageLogService.createRequestPart(message, appCode);
                        // 设置状态为已请求
                        apiMessageLogService.updateStatus(MessageStatusEnum.REQUESTED.name(), log.getRequestId());
                        // 发送次数加1
                        apiMessageLogService.incrementSendCount(log.getRequestId());

                    } else {
                        // 更新发送次数
                        apiMessageLogService.incrementSendCount(id);
                    }
                }
            });

        } else {
            // 创建日志
            apiMessageLogService.createRequestPart(message, appCode);
        }

    }

    /**
     * 发送请求消息
     * 适用场景：服务端收到生产者的业务消息时，需要找到订阅该消息主题的所有消费者，推送消息
     *
     * @param content 消息内容
     */
    public void sendMessage(String content) {
        // 查找是否有消息订阅者，无则直接终止后续处理
        List<String> subscriberList = apiMessageSubscriptionService.getSubscriberList(super.getTopic());
        if (CollectionUtils.isEmpty(subscriberList)) {
            return;
        }
        // 遍历订阅者，发送消息
        subscriberList.stream().forEach(appCode -> {
            // 数据权限过滤
            boolean hasDataPermission = dataPermissionFilter(content, appCode);
            if (hasDataPermission) {
                // 发送消息
                sendMessage(appCode, content, null);
            }
        });
    }


    /**
     * 数据权限过滤
     *
     * @param content 消息内容，通常是业务实体标识
     * @param appCode 应用编码
     * @return true，有权限，false 无权限
     */
    protected boolean dataPermissionFilter(String content, String appCode) {
        // 默认返回true，不进行数据权限控制，可被需要进行数据权限控制的子类覆写
        return true;
    }


}
