package tech.abc.platform.entityconfig.service.impl;

import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.ViewButtonTemplate;
import tech.abc.platform.entityconfig.mapper.ViewButtonTemplateMapper;
import tech.abc.platform.entityconfig.service.ViewButtonTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视图按钮模板 服务实现类
 *
 * @author wqliu
 * @date 2023-04-16
 */
@Service
@Slf4j
public class ViewButtonTemplateServiceImpl extends BaseServiceImpl<ViewButtonTemplateMapper, ViewButtonTemplate> implements ViewButtonTemplateService {
    @Override
    public ViewButtonTemplate init() {
        ViewButtonTemplate entity = new ViewButtonTemplate();
        // 默认值处理
        entity.setButtonType("LIST_PAGE");
        entity.setConfirmFlag("NO");
        entity.setPermissionFlag("YES");
        entity.setMoreFlag("NO");
        return entity;
    }

    @Override
    public void beforeAdd(ViewButtonTemplate entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewButtonTemplate::getName, entity.getName())
                .eq(ViewButtonTemplate::getButtonType, entity.getButtonType()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewButtonTemplate::getCode, entity.getCode())
                .eq(ViewButtonTemplate::getButtonType, entity.getButtonType()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(ViewButtonTemplate entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewButtonTemplate::getName, entity.getName())
                .eq(ViewButtonTemplate::getButtonType, entity.getButtonType())
                .ne(ViewButtonTemplate::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewButtonTemplate::getCode, entity.getCode())
                .eq(ViewButtonTemplate::getButtonType, entity.getButtonType())
                .ne(ViewButtonTemplate::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ViewButtonTemplate> list = this.lambdaQuery().in(ViewButtonTemplate::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    public ViewButtonTemplate getByCode(String code, String buttonType) {
        return this.lambdaQuery().eq(ViewButtonTemplate::getCode, code)
                .eq(ViewButtonTemplate::getButtonType, buttonType).one();
    }

}

