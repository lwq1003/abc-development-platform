package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.Portlet;


/**
 * 组件 服务类
 * @author wqliu
 * *
 */
public interface PortletService extends BaseService<Portlet> {

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

}
