package tech.abc.platform.notification.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.notification.config.NotificationConfig;
import tech.abc.platform.notification.entity.SystemMessage;
import tech.abc.platform.notification.mapper.SystemMessageMapper;
import tech.abc.platform.notification.server.global.ClientHolder;
import tech.abc.platform.notification.server.global.SseEmitterManager;
import tech.abc.platform.notification.service.SystemMessageService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统消息 服务实现类
 *
 * @author wqliu
 * @date 2023-05-27
 */
@Service
@Slf4j
public class SystemMessageServiceImpl extends BaseServiceImpl<SystemMessageMapper, SystemMessage> implements SystemMessageService {

    /**
     * 通知类型常量，网络套接字
     */
    public static final String WEB_SOCKET = "WebSocket";

    @Resource
    private NotificationConfig notificationConfig;


    @Override
    public SystemMessage init() {
        SystemMessage entity = new SystemMessage();
        // 默认值处理
        entity.setType("BUSINESS_MESSAGE");
        entity.setReadFlag("NO");
        return entity;
    }

    @Override
    public void beforeAdd(SystemMessage entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(SystemMessage entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<SystemMessage> list = this.lambdaQuery().in(SystemMessage::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getTitle());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(SystemMessage entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setTitle(entity.getTitle() + " 副本");
    }


    @Override
    public void sendMessage(String account, String title, String content) {
        SystemMessage entity = init();
        entity.setReceiver(account);
        entity.setTitle(title);
        entity.setContent(content);
        add(entity);

        if (notificationConfig.getNotificationType().equals(WEB_SOCKET)) {
            ClientHolder.sendMessage(entity);
        } else {
            SseEmitterManager.sendMessage(entity);
        }


    }

    @Override
    public SystemMessage setRead(String id) {
        SystemMessage entity = getEntity(id);
        entity.setReadFlag(YesOrNoEnum.YES.name());
        modify(entity);
        return entity;
    }

    @Override
    public void setAllRead() {
        LambdaUpdateWrapper<SystemMessage> lambdaUpdateWrapper = new UpdateWrapper<SystemMessage>().lambda().set(SystemMessage::getReadFlag,
                        YesOrNoEnum.YES.name())
                .eq(SystemMessage::getReceiver, UserUtil.getAccount())
                .eq(SystemMessage::getReadFlag, YesOrNoEnum.NO.name());
        this.update(lambdaUpdateWrapper);
    }

    @Override
    public Long getUnreadMessageCount() {
        return this.lambdaQuery().eq(SystemMessage::getReceiver, UserUtil.getAccount())
                .eq(SystemMessage::getReadFlag, YesOrNoEnum.NO.name())
                .count();
    }
}


