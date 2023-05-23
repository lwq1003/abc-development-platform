package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.PortalTemplate;


/**
 * 门户模板 服务类
 * @author wqliu
 * *
 */
public interface PortalTemplateService extends BaseService<PortalTemplate> {
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
