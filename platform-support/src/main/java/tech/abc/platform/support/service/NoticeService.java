package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * 通知公告 服务接口类
 *
 * @author wqliu
 * @date 2023-05-20
 */
public interface NoticeService extends BaseService<Notice> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


    /**
     * 获取组件数据
     *
     * @param count
     * @return
     */
    List<Notice> getPortletData(int count);


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
     * 置顶
     *
     * @param id 标识
     */
    void setTop(String id);

    /**
     * 取消置顶
     *
     * @param id 标识
     */
    void cancelTop(String id);

    /**
     * 更新阅读次数
     *
     * @param id 标识
     */
    void updateReadCount(String id);

}

