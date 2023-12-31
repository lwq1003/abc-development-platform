package tech.abc.platform.support.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.Portlet;

import java.util.List;
import java.util.Map;

/**
 * 组件 服务接口类
 *
 * @author wqliu
 * @date 2023-06-01
 */
public interface PortletService extends BaseService<Portlet> {

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
     * 获取组件列表
     *
     * @param queryWrapper 查询包装
     * @return {@link List}<{@link Portlet}>
     */
    List<Portlet> getPortletList(QueryWrapper<Portlet> queryWrapper);
}


