package tech.abc.platform.cip.message.framework.handler;

import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.BaseMessage;
import tech.abc.platform.cip.common.entity.MessageException;
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.entity.MessageTopic;
import tech.abc.platform.cip.service.*;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.utils.SpringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息处理父类
 *
 * @author wqliu
 * @date 2021-10-13
 **/
@Slf4j
public class MessageHandler {


    protected MessageLogService messageLogService = SpringUtil.getBean(MessageLogService.class);

    protected ActiveMessageService activeMessageService = SpringUtil.getBean(ActiveMessageService.class);


    protected AppService appService = SpringUtil.getBean(AppService.class);

    protected MessageTopicService messageTopicService = SpringUtil.getBean(MessageTopicService.class);


    protected MessagePermissionService apiMessagePermissionService =
            SpringUtil.getBean(MessagePermissionService.class);


    /**
     * 验证框架
     */
    protected void validateFramework(BaseMessage message, String appCode) {

        // 消息主题验证（是否存在及是否可用）
        validateTopic(message.getTopic());
        // 应用验证（是否存在及是否可用）
        validateAppCode(message.getPublishAppCode(), appCode);
        // 权限验证
        validatePermission(message.getPublishAppCode(), message.getTopic());
        // 时效性验证
        validatePublishTimeValid(message.getPublishTime());
    }


    /**
     * 验证权限
     *
     * @param publishAppCode 应用程序编码
     * @param topicCode      主题编码
     */
    protected void validatePermission(String publishAppCode, String topicCode) {

        boolean hasPermission = apiMessagePermissionService.checkPermission(publishAppCode, topicCode);
        if (hasPermission == false) {
            throw new MessageException("301", "应用无权限");
        }

    }

    /**
     * 验证时效性
     *
     * @param publishTimeString 发布时间字符串
     */
    protected void validatePublishTimeValid(String publishTimeString) {


        // 数据验证环节已验证可转换，此处不再处理转换异常
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date publishTime = null;
        try {
            publishTime = dateFormat.parse(publishTimeString);
        } catch (ParseException e) {
            // 前序环节已验证过日期格式，此处不会抛出异常，仅为编译通过
        }

        // 获取系统当前时间
        Date currentTime = new Date();
        // 比较时间
        long diff = Math.abs(currentTime.getTime() - publishTime.getTime());
        // 允许最大的时间差，单位毫秒
        int maxTimeSpan = 10 * 60 * 1000;
        if (diff > maxTimeSpan) {
            // 请求时间超出合理范围（10分钟）
            throw new MessageException("S401", "发布时间超出合理范围");
        }
    }

    /**
     * 验证应用
     *
     * @param publishAppCode 消息应用编码
     * @param appCode        通道应用编码
     */
    protected void validateAppCode(String publishAppCode, String appCode) {
        // 这地方需要对appCode判空，如登录操作，通道的appCode还不存在
        if (appCode != null && !appCode.equals(publishAppCode)) {
            log.error("应用标识与消息通道不一致,应用标识：{},消息通道{}", publishAppCode, appCode);
            throw new MessageException("S201", "应用标识与消息通道不一致");
        }

        try {
            App app = appService.getByCode(publishAppCode);
            if (app.getStatus().equals(StatusEnum.DEAD.name())) {
                throw new MessageException("S203", "应用被停用");
            }

        } catch (Exception ex) {
            throw new MessageException("S202", "应用标识无效");
        }
    }

    /**
     * 验证主题编码
     *
     * @param topicCode 主题编码
     */
    protected void validateTopic(String topicCode) {
        try {
            MessageTopic messageTopic = messageTopicService.getByCode(topicCode);
            if (messageTopic.getStatus().equals(StatusEnum.DEAD.name())) {
                throw new MessageException("S102", "消息主题不可用");
            }
        } catch (Exception ex) {
            throw new MessageException("S101", "消息主题不存在");
        }

    }


}
