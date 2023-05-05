package tech.abc.platform.cip.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.entity.MessageSubscription;
import tech.abc.platform.cip.mapper.MessageSubscriptionMapper;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.service.MessageSubscriptionService;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.common.base.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息订阅 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class MessageSubscriptionServiceImpl extends BaseServiceImpl<MessageSubscriptionMapper, MessageSubscription> implements MessageSubscriptionService {

    @Autowired
    private AppService appService;

    @Autowired
    private MessageTopicService messageTopicService;

    @Override
    public MessageSubscription init() {
        MessageSubscription entity = new MessageSubscription();
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(MessageSubscription entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(MessageSubscription entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<MessageSubscription> list = this.lambdaQuery().in(MessageSubscription::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getApp());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(MessageSubscription entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setApp(entity.getApp() + " 副本");
    }

    @Override
    public List<String> getSubscriberList(String code) {
        // 获取消息主题标识
        String topicId = messageTopicService.getIdByCode(code);
        // 获取订阅者列表
        List<MessageSubscription> idList = this.lambdaQuery().eq(MessageSubscription::getMessageTopic,
                        topicId)
                .list();
        if (CollectionUtils.isNotEmpty(idList)) {
            List<String> appIdList = idList.stream().map(x -> x.getApp()).collect(Collectors.toList());
            List<String> appCodeList = appService.lambdaQuery().in(App::getId, appIdList).list()
                    .stream().map(x -> x.getCode()).collect(Collectors.toList());
            return appCodeList;

        } else {
            return null;
        }

    }
}
