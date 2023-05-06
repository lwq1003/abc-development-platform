package tech.abc.platform.cip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.entity.MessagePermission;
import tech.abc.platform.cip.entity.MessageSubscription;
import tech.abc.platform.cip.entity.MessageTopic;
import tech.abc.platform.cip.exception.ApiMessageTopicExceptionEnum;
import tech.abc.platform.cip.mapper.MessageTopicMapper;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.service.MessagePermissionService;
import tech.abc.platform.cip.service.MessageSubscriptionService;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息主题 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class MessageTopicServiceImpl extends BaseServiceImpl<MessageTopicMapper, MessageTopic> implements MessageTopicService {
    @Autowired
    private MessagePermissionService messagePermissionService;

    @Autowired
    private MessageSubscriptionService messageSubscriptionService;

    @Autowired
    private AppService appService;

    @Override
    public MessageTopic init() {
        MessageTopic entity = new MessageTopic();
        // 默认值处理
        entity.setCategory("");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(MessageTopic entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(MessageTopic::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(MessageTopic::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(MessageTopic entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(MessageTopic::getName, entity.getName())
                    .ne(MessageTopic::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(MessageTopic::getCode, entity.getCode())
                    .ne(MessageTopic::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<MessageTopic> list = this.lambdaQuery().in(MessageTopic::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(MessageTopic entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }


    @Override
    public void enable(String id) {
        MessageTopic entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        MessageTopic entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    public List<MessageTopic> queryMessageTopicPermission(QueryWrapper<MessageTopic> queryWrapper, String appId,
                                                          String hasPermission) {
        // 查询出所有接口服务
        List<MessageTopic> serviceList = this.list(queryWrapper);
        // 查询当前应用标识拥有权限的接口服务标识列表
        List<MessagePermission> hasPermissionList =
                messagePermissionService.lambdaQuery().eq(MessagePermission::getApp, appId).list();
        List<String> hasPermissionIdList = hasPermissionList.stream().map(x -> x.getMessageTopic()).collect(Collectors.toList());
        // 设置授权标志位
        serviceList.stream().forEach(x -> {
            if (CollectionUtils.isNotEmpty(hasPermissionIdList)) {
                // 有至少1条权限记录
                if (hasPermissionIdList.contains(x.getId())) {
                    x.setHasPermission(YesOrNoEnum.YES.name());
                } else {
                    x.setHasPermission(YesOrNoEnum.NO.name());
                }
            } else {
                // 无任何权限，所有标识位置为NO
                x.setHasPermission(YesOrNoEnum.NO.name());
            }
        });
        if (StringUtils.isBlank(hasPermission)) {
            // 未设置查询条件，查询全部
            return serviceList;

        } else {
            // 只查询对应的数据列表
            return serviceList.stream().filter(x -> x.getHasPermission().equals(hasPermission)).collect(Collectors.toList());
        }

    }

    @Override
    public List<MessageTopic> queryMessageTopicSubscription(QueryWrapper<MessageTopic> queryWrapper, String hasSubscription) {


        // 获取app标识
        String appId = appService.getCurrentAppId();
        // 获取有权限的消息主题列表
        List<MessageTopic> messageTopicList = queryMessageTopicPermission(queryWrapper, appId, YesOrNoEnum.YES.name());


        // 查询当前应用标识已订阅的接口服务标识列表
        List<MessageSubscription> hasSubscriptionList =
                messageSubscriptionService.lambdaQuery().eq(MessageSubscription::getApp, appId).list();
        List<String> hasSubscriptionIdList = hasSubscriptionList.stream().map(x -> x.getMessageTopic()).collect(Collectors.toList());
        // 设置订阅标志位
        messageTopicList.stream().forEach(x -> {
            if (CollectionUtils.isNotEmpty(hasSubscriptionIdList)) {
                // 已订阅
                if (hasSubscriptionIdList.contains(x.getId())) {
                    x.setHasSubscription(YesOrNoEnum.YES.name());
                } else {
                    x.setHasSubscription(YesOrNoEnum.NO.name());
                }
            } else {
                // 无任何订阅，所有标识位置为NO
                x.setHasSubscription(YesOrNoEnum.NO.name());
            }
        });
        if (StringUtils.isBlank(hasSubscription)) {
            // 未设置查询条件，查询全部
            return messageTopicList;

        } else {
            // 只查询对应的数据列表
            return messageTopicList.stream().filter(x -> x.getHasSubscription().equals(hasSubscription)).collect(Collectors.toList());
        }
    }

    @Override
    public String getHandlerByCode(String code) {
        MessageTopic apiMessageTopic = getByCode(code);
        String handler = apiMessageTopic.getHandler();
        if (StringUtils.isBlank(handler)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_SET_HANDLER);
        }
        return handler;
    }

    @Override
    public String getSenderByCode(String code) {
        MessageTopic apiMessageTopic = getByCode(code);
        String sender = apiMessageTopic.getSender();
        if (StringUtils.isBlank(sender)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_SET_SENDER);
        }
        return sender;
    }

    @Override
    public String getResponseTopicCodeByCode(String code) {
        List<MessageTopic> list = this.lambdaQuery().eq(MessageTopic::getCode, code).list();
        if (CollectionUtils.isEmpty(list)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_EXIST);
        }
        String responseTopicCode = list.get(0).getResponseTopicCode();
        if (StringUtils.isBlank(responseTopicCode)) {
            throw new CustomException(ApiMessageTopicExceptionEnum.RESPONSE_TOPIC_NOT_SET_HANDLER);
        }
        return responseTopicCode;
    }

    @Override
    public String getIdByCode(String code) {
        return getByCode(code).getId();
    }

    @Override
    public MessageTopic getByCode(String code) {
        List<MessageTopic> list = this.lambdaQuery().eq(MessageTopic::getCode, code).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);

        } else {
            throw new CustomException(ApiMessageTopicExceptionEnum.TOPIC_NOT_EXIST);
        }
    }
}

