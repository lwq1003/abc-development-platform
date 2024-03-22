package tech.abc.platform.cip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.cip.entity.ApiServicePermission;
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.entity.MessagePermission;
import tech.abc.platform.cip.entity.MessageSubscription;
import tech.abc.platform.cip.exception.AppExceptionEnum;
import tech.abc.platform.cip.mapper.AppMapper;
import tech.abc.platform.cip.service.ApiServicePermissionService;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.cip.service.MessagePermissionService;
import tech.abc.platform.cip.service.MessageSubscriptionService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 应用 服务实现类
 *
 * @author wqliu
 * @date 2023-05-02
 */
@Service
@Slf4j
public class AppServiceImpl extends BaseServiceImpl<AppMapper, App> implements AppService {

    @Autowired
    private ApiServicePermissionService apiServicePermissionService;

    @Autowired
    private MessagePermissionService messageTopicPermissionService;

    @Autowired
    private MessageSubscriptionService messageSubscriptionService;


    @Override
    public App init() {
        App entity = new App();
        // 默认值处理
        entity.setIntegrationModel("CLIENT");
        entity.setStatus("NORMAL");

        // 创建应用时自动生成密钥
        entity.setSecretKey(UUID.randomUUID().toString());

        return entity;
    }

    @Override
    public void beforeAdd(App entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(App::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(App::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(App entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(App::getName, entity.getName())
                    .ne(App::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(App::getCode, entity.getCode())
                    .ne(App::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<App> list = this.lambdaQuery().in(App::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(App entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void enable(String id) {
        App entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        App entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    public void resetSecret(String id) {
        App entity = getEntity(id);
        entity.setSecretKey(UUID.randomUUID().toString());
        modify(entity);
    }

    @Override
    public void authorize(String appCode, String appSecret) {
        List<App> list = this.lambdaQuery().eq(App::getCode, appCode)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            throw new CustomException(AppExceptionEnum.APP_CODE_OR_SECRET_ERROR);
        } else {
            App app = list.get(0);
            String secret = app.getSecretKey();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(secret, appSecret)) {
                throw new CustomException(AppExceptionEnum.APP_CODE_OR_SECRET_ERROR);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantApiPermission(String appId, List<String> apiServiceIdList) {

        if (CollectionUtils.isNotEmpty(apiServiceIdList)) {
            // 移除末尾空元素
            apiServiceIdList.removeIf(x -> StringUtils.isEmpty(x));

            for (String apiServiceId : apiServiceIdList) {
                // 查询是否已存在授权记录
                long count = apiServicePermissionService.lambdaQuery().eq(ApiServicePermission::getApp, appId)
                        .eq(ApiServicePermission::getApiService, apiServiceId).count();
                // 不存在时授权
                if (count == 0) {
                    ApiServicePermission entity = new ApiServicePermission();
                    entity.setApp(appId);
                    entity.setApiService(apiServiceId);
                    apiServicePermissionService.add(entity);
                }

            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawApiPermission(String appId, List<String> apiServiceIdList) {
        if (CollectionUtils.isNotEmpty(apiServiceIdList)) {
            QueryWrapper<ApiServicePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(ApiServicePermission::getApp, appId)
                    .in(ApiServicePermission::getApiService, apiServiceIdList);
            apiServicePermissionService.remove(queryWrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantMessageTopicPermission(String appId, List<String> apiMessageTopicIdList) {

        if (CollectionUtils.isNotEmpty(apiMessageTopicIdList)) {
            // 移除末尾空元素
            apiMessageTopicIdList.removeIf(x -> StringUtils.isEmpty(x));

            for (String apiMessageTopicId : apiMessageTopicIdList) {
                // 查询是否已存在授权记录
                long count = messageTopicPermissionService.lambdaQuery().eq(MessagePermission::getApp, appId)
                        .eq(MessagePermission::getMessageTopic, apiMessageTopicId).count();
                // 不存在时授权
                if (count == 0) {
                    MessagePermission entity = new MessagePermission();
                    entity.setApp(appId);
                    entity.setMessageTopic(apiMessageTopicId);
                    messageTopicPermissionService.add(entity);
                }

            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawMessageTopicPermission(String appId, List<String> apiMessageTopicIdList) {
        if (CollectionUtils.isNotEmpty(apiMessageTopicIdList)) {
            QueryWrapper<MessagePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(MessagePermission::getApp, appId)
                    .in(MessagePermission::getMessageTopic, apiMessageTopicIdList);
            messageTopicPermissionService.remove(queryWrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subscribeMessage(List<String> messageTopicIdList) {
        // 获取app标识
        String appId = getCurrentAppId();

        if (CollectionUtils.isNotEmpty(messageTopicIdList)) {
            // 移除末尾空元素
            messageTopicIdList.removeIf(x -> StringUtils.isEmpty(x));

            for (String messageTopicId : messageTopicIdList) {
                // 查询是否已存在授权记录
                long count = messageSubscriptionService.lambdaQuery().eq(MessageSubscription::getApp, appId)
                        .eq(MessageSubscription::getMessageTopic, messageTopicId).count();
                // 不存在时授权
                if (count == 0) {
                    MessageSubscription entity = new MessageSubscription();
                    entity.setApp(appId);
                    entity.setMessageTopic(messageTopicId);
                    messageSubscriptionService.add(entity);
                }

            }
        }
    }

    @Override
    public void unsubscribeMessage(List<String> messageTopicIdList) {
        if (CollectionUtils.isNotEmpty(messageTopicIdList)) {
            // 获取app标识
            String appId = getCurrentAppId();

            QueryWrapper<MessageSubscription> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(MessageSubscription::getApp, appId)
                    .in(MessageSubscription::getMessageTopic, messageTopicIdList);
            messageSubscriptionService.remove(queryWrapper);
        }

    }

    @Override
    public String getIntegrationModelByAppCode(String appCode) {
        return getByCode(appCode).getIntegrationModel();

    }

    @Override
    public App getByCode(String code) {
        List<App> list = this.lambdaQuery().eq(App::getCode, code).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);

        } else {
            throw new CustomException(AppExceptionEnum.APP_CODE_NOT_EXIST);
        }
    }

    @Override
    public String getCurrentAppId() {
        // 获取当前账号
        String account = UserUtil.getAccount();
        // 查找与账号编码相同的app
        App app = getByCode(account);
        // 获取app标识
        return app.getId();
    }


}

