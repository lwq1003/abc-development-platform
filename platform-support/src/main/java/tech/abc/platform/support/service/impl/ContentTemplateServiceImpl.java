package tech.abc.platform.support.service.impl;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.ContentTemplate;
import tech.abc.platform.support.enums.ContentTemplateTypeEnum;
import tech.abc.platform.support.exception.ContentTemplateExceptionEnum;
import tech.abc.platform.support.mapper.ContentTemplateMapper;
import tech.abc.platform.support.service.ContentTemplateService;

import java.io.StringWriter;

import static freemarker.template.Configuration.VERSION_2_3_28;


/**
 * 内容模板 服务实现类
 *
 * @author wqliu
 */
@Service
public class ContentTemplateServiceImpl extends BaseServiceImpl<ContentTemplateMapper, ContentTemplate> implements ContentTemplateService {
    @Override
    public ContentTemplate init() {
        ContentTemplate entity = new ContentTemplate();
        entity.setType(ContentTemplateTypeEnum.TEXT.name());
        return entity;
    }

    @Override
    protected void beforeAdd(ContentTemplate entity) {
        // 验证同父节点下有没有相同名称的内容模板
        long count = super.lambdaQuery().eq(ContentTemplate::getCategoryId, entity.getCategoryId())
                .eq(ContentTemplate::getName, entity.getName())
                .count();
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST_IN_SAME_NODE);
        }
        // 验证全局是否有相同的code
        long codeCount = super.lambdaQuery().eq(ContentTemplate::getCode, entity.getCode())
                .count();
        if (codeCount > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }

    @Override
    protected void beforeModify(ContentTemplate entity) {
        // 验证同父节点下有没有相同名称的内容模板
        long count = super.lambdaQuery().eq(ContentTemplate::getCategoryId, entity.getCategoryId())
                .eq(ContentTemplate::getName, entity.getName())
                .ne(ContentTemplate::getId, entity.getId())
                .count();
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST_IN_SAME_NODE);
        }
        // 验证全局是否有相同的code
        long codeCount = super.lambdaQuery().eq(ContentTemplate::getCode, entity.getCode())
                .ne(ContentTemplate::getId, entity.getId())
                .count();
        if (codeCount > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }

    @Override
    public String getContent(String contentTemplateCode, Object param) {
        // 获取内容模板
        ContentTemplate contentTemplate = this.lambdaQuery().eq(ContentTemplate::getCode, contentTemplateCode).one();
        if (contentTemplate == null) {
            throw new CustomException(ContentTemplateExceptionEnum.NOT_EXIST);
        }
        // 获取模板内容
        String content = contentTemplate.getContent();

        // 使用freemarker格式化
        Configuration cfg = new Configuration(VERSION_2_3_28);
        String templateName = "contentTemplate";
        StringTemplateLoader stl = new StringTemplateLoader();
        stl.putTemplate(templateName, content);
        cfg.setTemplateLoader(stl);
        try {
            Template template = cfg.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            template.process(param, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new CustomException(ContentTemplateExceptionEnum.OPERATION_FAILED);
        }
    }

}
