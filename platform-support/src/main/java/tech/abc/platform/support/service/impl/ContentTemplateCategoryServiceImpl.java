package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.ContentTemplate;
import tech.abc.platform.support.entity.ContentTemplateCategory;
import tech.abc.platform.support.exception.ContentTemplateExceptionEnum;
import tech.abc.platform.support.mapper.ContentTemplateCategoryMapper;
import tech.abc.platform.support.service.ContentTemplateCategoryService;
import tech.abc.platform.support.service.ContentTemplateService;

/**
 * 内容模板分类 服务实现类
 *
 * @author wqliu
 */
@Service
public class ContentTemplateCategoryServiceImpl extends BaseServiceImpl<ContentTemplateCategoryMapper, ContentTemplateCategory> implements ContentTemplateCategoryService {

    @Autowired
    private ContentTemplateService contentTemplateService;


    @Override
    public ContentTemplateCategory init() {
        ContentTemplateCategory entity = new ContentTemplateCategory();

        return entity;
    }

    @Override
    public void beforeAdd(ContentTemplateCategory entity) {
        // 验证同节点下是否存在名称相同的组织机构
        QueryWrapper<ContentTemplateCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ContentTemplateCategory::getName, entity.getName())
                .eq(ContentTemplateCategory::getParentId, entity.getParentId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST_IN_SAME_NODE);
        }

    }


    @Override
    public void beforeModify(ContentTemplateCategory entity) {

        // 验证同节点下是否存在名称相同的组织机构
        QueryWrapper<ContentTemplateCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ContentTemplateCategory::getName, entity.getName())
                .eq(ContentTemplateCategory::getParentId, entity.getParentId())
                .ne(ContentTemplateCategory::getId, entity.getId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.NAME_EXIST_IN_SAME_NODE);
        }
    }


    @Override
    public void beforeRemove(ContentTemplateCategory entity) {
        // 验证是否有下级
        if (super.lambdaQuery().eq(ContentTemplateCategory::getParentId, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.HAS_CHILDREN);
        }

        // 验证是否存在内容模板
        QueryWrapper<ContentTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ContentTemplate::getCategoryId, entity.getId());
        long count = contentTemplateService.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(ContentTemplateExceptionEnum.HAS_CHILDREN);
        }
    }


}
