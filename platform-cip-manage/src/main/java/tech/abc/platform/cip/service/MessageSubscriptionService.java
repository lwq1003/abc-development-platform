package tech.abc.platform.cip.service;

import tech.abc.platform.cip.entity.MessageSubscription;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 消息订阅 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface MessageSubscriptionService extends BaseService<MessageSubscription> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取消息主题订阅者
     *
     * @param topic 消息主题
     * @return 该消息主题的消息主题订阅者编码列表
     */
    List<String> getSubscriberList(String topic);
}

