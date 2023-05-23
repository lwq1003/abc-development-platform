package tech.abc.platform.support.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.PortletCategory;

import java.util.List;


/**
 * 组件类别 服务类
 * @author wqliu
 * *
 */
public interface PortletCategoryService extends BaseService<PortletCategory> {

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
     *  获取组件类别及组件列表
     * @param queryWrapper
     * @return
     */
    List<PortletCategory> getPortletList(QueryWrapper<PortletCategory> queryWrapper);
}
