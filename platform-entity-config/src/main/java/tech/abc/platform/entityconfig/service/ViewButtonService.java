package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.ViewButton;
import tech.abc.platform.entityconfig.vo.SortedObject;

import java.util.List;
import java.util.Map;

/**
 * 视图按钮 服务接口类
 *
 * @author wqliu
 * @date 2023-04-16
 */
public interface ViewButtonService extends BaseService<ViewButton> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 更新按钮排序
     *
     * @param viewId     视图id
     * @param buttonList 按钮列表
     */
    void updateButtonSort(String viewId, List<SortedObject> buttonList);

    /**
     * 清空按钮
     *
     * @param viewId     视图id
     * @param buttonType 按钮类型
     */
    void clear(String viewId, String buttonType);

    /**
     * 通过模板添加
     *
     * @param viewId         视图id
     * @param buttonType     按钮类型
     * @param buttonCodeList 按钮代码列表
     */
    void addFromTemplate(String viewId, String buttonType, List<String> buttonCodeList);


    /**
     * 获取按钮列表
     *
     * @param viewId     视图id
     * @param buttonType 按钮类型
     * @param moreFlag   用于更多
     * @return {@link List}<{@link ViewButton}>
     */
    List<ViewButton> listByViewAndType(String viewId, String buttonType, String moreFlag);

    /**
     * 获取按钮列表
     *
     * @param viewId     视图id
     * @param buttonType 按钮类型
     * @return {@link List}<{@link ViewButton}>
     */
    List<ViewButton> listByViewAndType(String viewId, String buttonType);
}

