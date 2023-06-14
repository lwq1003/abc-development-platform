package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.ContentTemplate;

import java.util.List;
import java.util.Map;

/**
 * 内容模板 服务接口类
 *
 * @author wqliu
 * @date 2023-05-31
 */
public interface ContentTemplateService extends BaseService<ContentTemplate> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 根据内容模板获取内容
     *
     * @param contentTemplateCode 内容模板编码
     * @param param               参数值
     * @return
     */
    public String getContent(String contentTemplateCode, Object param);


}


