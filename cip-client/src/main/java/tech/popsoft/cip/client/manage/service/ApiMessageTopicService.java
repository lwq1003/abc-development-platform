package tech.popsoft.cip.client.manage.service;

import tech.abc.platform.common.base.BaseService;
import tech.popsoft.cip.client.manage.entity.ApiMessageTopic;


/**
 * 消息主题 服务类
 *
 * @author wqliu
 * @date 2021-08-21
 */
public interface ApiMessageTopicService extends BaseService<ApiMessageTopic> {

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
     * 根据消息主题编码获取处理类
     *
     * @param code 消息主题
     * @return 处理类名（含路径）
     */
    String getHandlerByCode(String code);

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
    ApiMessageTopic getByCode(String code);

    /**
     * 根据消息主题编码获取发送类
     *
     * @param code 消息主题
     * @return 发送类名（含路径）
     */
    String getSenderByCode(String code);

}
