package tech.abc.platform.support.service.impl;

import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.PortalTemplate;
import tech.abc.platform.support.mapper.PortalTemplateMapper;
import tech.abc.platform.support.service.PortalTemplateService;

/**
 * 门户模板 服务实现类
 *
 * @author wqliu
 */
@Service
public class PortalTemplateServiceImpl extends BaseServiceImpl<PortalTemplateMapper, PortalTemplate> implements PortalTemplateService {
    @Override
    public PortalTemplate init() {
        PortalTemplate entity = new PortalTemplate();
        entity.setStatus(StatusEnum.NORMAL.name());
        return entity;
    }

    @Override
    public void beforeAdd(PortalTemplate entity) {
        // 验证名称唯一
        if (this.lambdaQuery().eq(PortalTemplate::getName, entity.getName()).count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }

        // 验证编码唯一
        if (this.lambdaQuery().eq(PortalTemplate::getCode, entity.getCode()).count() > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    @Override
    public void beforeModify(PortalTemplate entity) {
        // 验证名称唯一
        if (this.lambdaQuery().eq(PortalTemplate::getName, entity.getName())
                .ne(PortalTemplate::getId, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }

        // 验证编码唯一
        if (this.lambdaQuery().eq(PortalTemplate::getCode, entity.getCode())
                .ne(PortalTemplate::getId, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    @Override
    public void enable(String id) {
        PortalTemplate entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        PortalTemplate entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }




}
