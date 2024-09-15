package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.EntityModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 实体模型属性 服务接口类
 *
 * @author wqliu
 * @date 2023-04-15
 */
public interface EntityModelPropertyService extends BaseService<EntityModelProperty> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 通过实体模型标识获取实体模型属性列表
     *
     * @param entityModelId 实体模型id
     * @return {@link List}<{@link EntityModelProperty}>
     */
    List<EntityModelProperty> getByEntityModelId(String entityModelId);

    /**
     * 通过实体模型标识获取实体模型属性列表(仅数据库存储）
     *
     * @param entityModelId 实体模型id
     * @return {@link List}<{@link EntityModelProperty}>
     */
    List<EntityModelProperty> getDatabaseStoreListByEntityModelId(String entityModelId);


    /**
     * 通过实体模型标识获取实体模型属性列表(仅非数据库存储）
     *
     * @param entityModelId 实体模型id
     * @return {@link List}<{@link EntityModelProperty}>
     */
    List<EntityModelProperty> getNoDatabaseStoreListByEntityModelId(String entityModelId);


    /**
     * 通过实体模型标识获取包含父类在内的所有实体模型属性列表
     *
     * @param entityModelId 实体模型id
     * @return {@link List}<{@link EntityModelProperty}>
     */
    List<EntityModelProperty> getFullPropertyByEntityModelId(String entityModelId);


    /**
     * 通过实体模型编码获取包含父类在内的所有实体模型属性列表
     *
     * @param entityModelCode 实体模型编码
     * @return {@link List}<{@link EntityModelProperty}>
     */
    List<EntityModelProperty> getFullPropertyByEntityModelCode(String entityModelCode);


    /**
     * 通过实体模型代码获取实体模型属性列表
     *
     * @param entityModelCode 实体模型代码
     * @return {@link List}<{@link EntityModelProperty}>
     */
    List<EntityModelProperty> getByEntityModelCode(String entityModelCode);

}

