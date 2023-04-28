package tech.abc.platform.entityconfig.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.entityconfig.entity.ViewButton;
import tech.abc.platform.entityconfig.entity.ViewButtonTemplate;

import java.util.List;
import java.util.Map;

/**
 * 视图按钮模板 服务接口类
 *
 * @author wqliu
 * @date 2023-04-16
 */
public interface ViewButtonTemplateService extends BaseService<ViewButtonTemplate> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


    /**
     * 通过编码获取按钮
     *
     * @param code       代码
     * @param buttonType 按钮类型
     * @return {@link ViewButton}
     */
    ViewButtonTemplate getByCode(String code, String buttonType);

}

