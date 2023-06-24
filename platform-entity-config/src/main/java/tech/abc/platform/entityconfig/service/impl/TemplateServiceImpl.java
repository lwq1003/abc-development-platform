package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.Template;
import tech.abc.platform.entityconfig.mapper.TemplateMapper;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.entityconfig.service.TemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板 服务实现类
 *
 * @author wqliu
 * @date 2023-06-24
 */
@Service
@Slf4j
public class TemplateServiceImpl extends BaseServiceImpl<TemplateMapper, Template> implements TemplateService {
    @Autowired
    private EntityModelService entityModelService;

    @Override
    public Template init() {
        Template entity = new Template();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        // 默认值处理
        entity.setYesOrNo("");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(Template entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Template::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(Template::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }

        // 自动生成流水号
        entity.setSerialNo(entityModelService.generateSerialNo("Template"));
    }

    @Override
    public void beforeModify(Template entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Template::getName, entity.getName())
                    .ne(Template::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(Template::getCode, entity.getCode())
                    .ne(Template::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Template> list = this.lambdaQuery().in(Template::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Template entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

}

