package tech.abc.platform.cip.service;

import tech.abc.platform.cip.entity.App;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 应用 服务接口类
 *
 * @author wqliu
 * @date 2023-05-02
 */
public interface AppService extends BaseService<App> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 启用
     *
     * @param id 标识
     */
    void enable(String id);

    /**
     * 停用
     *
     * @param id 标识
     */
    void disable(String id);

    /**
     * 重置密钥
     *
     * @param id 标识
     */
    void resetSecret(String id);

    /**
     * 认证
     *
     * @param appCode   编码
     * @param appSecret 密钥
     */
    void authorize(String appCode, String appSecret);


    /**
     * 授予接口服务权限
     *
     * @param appId            应用标识
     * @param apiServiceIdList 接口服务标识列表
     */
    void grantApiPermission(String appId, List<String> apiServiceIdList);

    /**
     * 收回接口服务权限
     *
     * @param appId            应用标识
     * @param apiServiceIdList 接口服务标识列表
     */
    void withdrawApiPermission(String appId, List<String> apiServiceIdList);

    /**
     * 授予消息主题权限
     *
     * @param appId                 应用标识
     * @param apiMessageTopicIdList 消息主题标识列表
     */
    void grantMessageTopicPermission(String appId, List<String> apiMessageTopicIdList);

    /**
     * 收回消息主题权限
     *
     * @param appId                 应用标识
     * @param apiMessageTopicIdList 接口服务标识列表
     */
    void withdrawMessageTopicPermission(String appId, List<String> apiMessageTopicIdList);


    /**
     * 获取处理模式
     *
     * @param appCode
     * @return
     */
    String getIntegrationModelByAppCode(String appCode);


    /**
     * 根据编码获取对象
     *
     * @param code 编码
     * @return 对象
     */
    App getByCode(String code);

}

