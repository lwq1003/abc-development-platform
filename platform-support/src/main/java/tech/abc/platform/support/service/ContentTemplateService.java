package tech.abc.platform.support.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.support.entity.ContentTemplate;


/**
 * 内容模板 服务类
 * @author wqliu
 * *
 */
public interface ContentTemplateService extends BaseService<ContentTemplate> {

    /**
     * 根据内容模板获取内容
     * @param contentTemplateCode 内容模板编码
     * @param param 参数值
     * @return
     */
    public String getContent(String contentTemplateCode, Object param);



}
