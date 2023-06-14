package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.DesktopTemplate;

import java.util.List;
import java.util.Map;

/**
 * 桌面模板 服务接口类
 *
 * @author wqliu
 * @date 2023-06-02
 */
public interface DesktopTemplateService extends BaseService<DesktopTemplate> {

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
}


