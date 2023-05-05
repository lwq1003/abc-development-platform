package tech.abc.platform.cip.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.entity.MessagePermission;
import tech.abc.platform.cip.entity.MessageTopic;
import tech.abc.platform.cip.mapper.MessagePermissionMapper;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.service.MessagePermissionService;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.common.base.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息权限 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class MessagePermissionServiceImpl extends BaseServiceImpl<MessagePermissionMapper, MessagePermission> implements MessagePermissionService {

    @Autowired
    private AppService appService;

    @Autowired
    private MessageTopicService messageTopicService;


    @Override
    public MessagePermission init() {
        MessagePermission entity = new MessagePermission();
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(MessagePermission entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(MessagePermission entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<MessagePermission> list = this.lambdaQuery().in(MessagePermission::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getApp());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(MessagePermission entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setApp(entity.getApp() + " 副本");
    }

    @Override
    public boolean checkPermission(String appCode, String topicCode) {
        App app = appService.getByCode(appCode);
        MessageTopic messageTopic = messageTopicService.getByCode(topicCode);
        long count = this.lambdaQuery().eq(MessagePermission::getApp, app.getId())
                .eq(MessagePermission::getMessageTopic, messageTopic.getId())
                .count();
        return count > 0;

    }

}

