package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.Entity;

import java.util.List;
import java.util.Map;

/**
 * 实体 服务接口类
 *
 * @author wqliu
 * @date 2023-04-11
 */
public interface EntityService extends BaseService<Entity> {


    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


    /**
     * 通过代码获取实体对象
     *
     * @param code 代码
     * @return {@link Entity}
     */
    Entity getByCode(String code);

    /**
     * 生成库表
     *
     * @param id id  实体标识或实体标识列表
     */
    void generateTable(String id);

    /**
     * 生成代码
     *
     * @param id id  实体标识或实体标识列表
     */
    void generateCode(String id);


}


