package tech.abc.platform.system.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.Module;

import java.util.List;
import java.util.Map;

/**
 * 模块 服务接口类
 *
 * @author wqliu
 * @date 2023-04-09
 */
public interface ModuleService extends BaseService<Module> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);
}

