package tech.abc.platform.support.service.impl;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.ContentTemplate;
import tech.abc.platform.support.exception.ContentTemplateExceptionEnum;
import tech.abc.platform.support.mapper.ContentTemplateMapper;
import tech.abc.platform.support.service.ContentTemplateService;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_28;

/**
 * 内容模板 服务实现类
 *
 * @author wqliu
 * @date 2023-05-31
 */
@Service
@Slf4j
public class ContentTemplateServiceImpl extends BaseServiceImpl<ContentTemplateMapper, ContentTemplate> implements ContentTemplateService {
    @Override
    public ContentTemplate init() {
        ContentTemplate entity = new ContentTemplate();
        // 默认值处理
        entity.setCategory("");
        entity.setType("TEXT");
        return entity;
    }

    @Override
    public void beforeAdd(ContentTemplate entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(ContentTemplate::getName, entity.getName())
                    .eq(ContentTemplate::getCategory, entity.getCategory()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(ContentTemplate::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(ContentTemplate entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(ContentTemplate::getName, entity.getName())
                    .eq(ContentTemplate::getCategory, entity.getCategory())
                    .ne(ContentTemplate::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(ContentTemplate::getCode, entity.getCode())
                    .ne(ContentTemplate::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }


    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ContentTemplate> list = this.lambdaQuery().in(ContentTemplate::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(ContentTemplate entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
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

