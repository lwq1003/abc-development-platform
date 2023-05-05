package tech.abc.platform.cip.service;

import tech.abc.platform.cip.entity.MessagePermission;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 消息权限 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface MessagePermissionService extends BaseService<MessagePermission> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 确认是否有权限
     *
     * @param appCode   应用编码
     * @param topicCode 消息主题编码
     * @return true 有 false 无
     */
    boolean checkPermission(String appCode, String topicCode);

}


