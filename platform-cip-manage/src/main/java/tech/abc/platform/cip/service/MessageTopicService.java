package tech.abc.platform.cip.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.cip.entity.MessageTopic;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 消息主题 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface MessageTopicService extends BaseService<MessageTopic> {

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
     * 查询指定应用对应的消息主题权限列表
     *
     * @param queryWrapper  查询条件
     * @param appId         应用标识
     * @param hasPermission 是否已授权
     * @return
     */
    List<MessageTopic> queryMessageTopicPermission(QueryWrapper<MessageTopic> queryWrapper, String appId, String hasPermission);


    /**
     * 查询当前用户对应的应用对应的消息订阅列表
     *
     * @param queryWrapper    查询条件
     * @param hasSubscription 是否已订阅
     * @return
     */
    List<MessageTopic> queryMessageTopicSubscription(QueryWrapper<MessageTopic> queryWrapper, String hasSubscription);


    /**
     * 根据消息主题编码获取响应主题编码
     *
     * @param code 消息主题
     * @return 响应主题编码
     */
    String getResponseTopicCodeByCode(String code);


    /**
     * 根据消息主题编码获取消息主题标识
     *
     * @param code 消息主题编码
     * @return 消息主题标识
     */
    String getIdByCode(String code);


    /**
     * 根据消息主题编码获取消息主题对象
     *
     * @param code 消息主题编码
     * @return 消息主题对象
     */
    MessageTopic getByCode(String code);


    /**
     * 根据消息主题编码获取处理类
     *
     * @param code 消息主题
     * @return 处理类名（含路径）
     */
    String getHandlerByCode(String code);


    /**
     * 根据消息主题编码获取发送类
     *
     * @param code 消息主题
     * @return 发送类名（含路径）
     */
    String getSenderByCode(String code);
}

