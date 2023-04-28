package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.ViewQueryResult;
import tech.abc.platform.entityconfig.vo.SortedObject;

import java.util.List;
import java.util.Map;

/**
 * 视图查询结果 服务接口类
 *
 * @author wqliu
 * @date 2023-04-16
 */
public interface ViewQueryResultService extends BaseService<ViewQueryResult> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 通过模型属性添加查询结果
     *
     * @param viewId 视图id
     * @param code   模型属性代码
     */
    void addFromModelProperty(String viewId, String code);

    /**
     * 更新排序
     *
     * @param viewId 视图id
     * @param list   排序列表
     */
    void updateSort(String viewId, List<SortedObject> list);

    /**
     * 清空
     *
     * @param viewId 视图id
     */
    void clear(String viewId);

    /**
     * 通过模型属性批量添加
     *
     * @param viewId 视图id
     */
    void addBatchFromModelProperty(String viewId);

    /**
     * 通过视图标识获取查询结果列表
     *
     * @param viewId 视图标识
     * @return {@link List}<{@link ViewQueryResult}>
     */
    List<ViewQueryResult> listByView(String viewId);
}
