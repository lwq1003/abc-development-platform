package tech.abc.platform.notification.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.notification.entity.SystemMessage;

import java.util.List;
import java.util.Map;

/**
 * 系统消息 服务接口类
 *
 * @author wqliu
 * @date 2023-05-27
 */
public interface SystemMessageService extends BaseService<SystemMessage> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 发送消息
     *
     * @param account 接收用户账号
     * @param title   标题
     * @param content 内容
     */
    void sendMessage(String account, String title, String content);

    /**
     * 设置单条消息已读
     *
     * @param id 消息标识
     * @return 消息对象
     */
    SystemMessage setRead(String id);

    /**
     * 设置所有消息已读
     */
    void setAllRead();

    /**
     * 获取未读消息数量
     *
     * @return
     */
    Long getUnreadMessageCount();
}

