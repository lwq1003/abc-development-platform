package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.EntityModelDataPermission;

import java.util.List;
import java.util.Map;

/**
 * 实体模型数据权限 服务接口类
 *
 * @author wqliu
 * @date 2024-08-03
 */
public interface EntityModelDataPermissionService extends BaseService<EntityModelDataPermission> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 根据模型标识获取数据权限配置
     *
     * @param modelId 模型标识
     * @return 实体模型数据权限实体类
     */
    EntityModelDataPermission getOrInit(String modelId);


    /**
     * 根据库表名获取数据权限过滤sql片段
     *
     * @param tableName 库表名
     * @return 数据权限过滤sql片段
     */
    String getDataPermissionSqlPart(String tableName);

    /**
     * 生成sql片段
     *
     * @param id   模型标识
     * @param rule 筛选规则
     * @return sql片段
     */
    String generateSqlPart(String id, String rule);
}

