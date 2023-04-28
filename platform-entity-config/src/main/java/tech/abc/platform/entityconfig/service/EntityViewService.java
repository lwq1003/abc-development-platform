package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.EntityView;

import java.util.List;
import java.util.Map;

/**
 * 实体视图 服务接口类
 *
 * @author wqliu
 * @date 2023-04-13
 */
public interface EntityViewService extends BaseService<EntityView> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


    /**
     * 通过实体模型标识获取视图列表
     *
     * @param modelId 模型标识
     * @return {@link List}<{@link EntityModel}>
     */
    List<EntityView> getByModelId(String modelId);


    /**
     * 通过实体标识获取视图列表
     *
     * @param entityId 实体id
     * @return {@link List}<{@link EntityView}>
     */
    List<EntityView> getByEntityId(String entityId);

    /**
     * 通过实体编码获取主参照视图编码
     *
     * @param entityCode 实体代码
     * @return {@link String}
     */
    String getMainReferenceViewCode(String entityCode);

}

