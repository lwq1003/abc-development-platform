package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.EntityModel;

import java.util.List;
import java.util.Map;

/**
 * 实体模型 服务接口类
 *
 * @author wqliu
 * @date 2023-04-13
 */
public interface EntityModelService extends BaseService<EntityModel> {


    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


    /**
     * 通过实体标识获取模型列表
     *
     * @param entityId 实体标识
     * @return {@link List}<{@link EntityModel}>
     */
    List<EntityModel> getByEntityId(String entityId);


    /**
     * 通过代码获取标识
     *
     * @param code 代码
     * @return {@link String}
     */
    String getIdByCode(String code);


    /**
     * 创建表
     *
     * @param tableName          表名
     * @param tableComment       表备注
     * @param tableFieldInfoList 字段信息列表
     */
    void createTable(String tableName, String tableComment, List tableFieldInfoList);

}

