package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.ViewButton;
import tech.abc.platform.entityconfig.entity.ViewButtonTemplate;
import tech.abc.platform.entityconfig.mapper.ViewButtonMapper;
import tech.abc.platform.entityconfig.service.ViewButtonService;
import tech.abc.platform.entityconfig.vo.SortedObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视图按钮 服务实现类
 *
 * @author wqliu
 * @date 2023-04-16
 */
@Service
@Slf4j
public class ViewButtonServiceImpl extends BaseServiceImpl<ViewButtonMapper, ViewButton> implements ViewButtonService {

    @Autowired
    private ViewButtonTemplateServiceImpl viewButtonTemplateService;


    @Override
    public ViewButton init() {
        ViewButton entity = new ViewButton();
        // 默认值处理
        entity.setButtonType("LIST_PAGE");
        entity.setConfirmFlag("NO");
        entity.setPermissionFlag("YES");
        entity.setMoreFlag("NO");
        return entity;
    }

    @Override
    public void beforeAdd(ViewButton entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewButton::getName, entity.getName())
                .eq(ViewButton::getView, entity.getVersion())
                .eq(ViewButton::getButtonType, entity.getButtonType()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewButton::getCode, entity.getCode())
                .eq(ViewButton::getView, entity.getVersion())
                .eq(ViewButton::getButtonType, entity.getButtonType()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(ViewButton entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewButton::getName, entity.getName())
                .eq(ViewButton::getView, entity.getVersion())
                .eq(ViewButton::getButtonType, entity.getButtonType())
                .ne(ViewButton::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewButton::getCode, entity.getCode())
                .eq(ViewButton::getView, entity.getVersion())
                .eq(ViewButton::getButtonType, entity.getButtonType())
                .ne(ViewButton::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ViewButton> list = this.lambdaQuery().in(ViewButton::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateButtonSort(String viewId, List<SortedObject> buttonList) {
        buttonList.forEach(x -> {
            ViewButton entity = this.lambdaQuery().eq(ViewButton::getView, viewId)
                    .eq(ViewButton::getCode, x.getCode()).one();
            entity.setOrderNo(StringUtils.leftPad(x.getIndex().toString(), 2, "0"));
            modify(entity);

        });
    }

    @Override
    public void clear(String viewId, String buttonType) {
        QueryWrapper<ViewButton> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewButton::getView, viewId)
                .eq(ViewButton::getButtonType, buttonType);
        this.remove(queryWrapper);
    }

    @Override
    public void addFromTemplate(String viewId, String buttonType, List<String> buttonCodeList) {

        buttonCodeList.forEach(item -> {
            ViewButtonTemplate viewButtonTemplate = viewButtonTemplateService.getByCode(item, buttonType);
            ViewButton entity = mapperFacade.map(viewButtonTemplate, ViewButton.class);
            entity.setView(viewId);
            entity.setId(null);
            add(entity);

        });

    }


    @Override
    public List<ViewButton> listByViewAndType(String viewId, String buttonType, String moreFlag) {

        return this.lambdaQuery().eq(ViewButton::getView, viewId)
                .eq(ViewButton::getButtonType, buttonType)

                .eq(ViewButton::getMoreFlag, moreFlag)
                .orderByAsc(ViewButton::getOrderNo).list();

    }

    @Override
    public List<ViewButton> listByViewAndType(String viewId, String buttonType) {

        return this.lambdaQuery().eq(ViewButton::getView, viewId)
                .eq(ViewButton::getButtonType, buttonType)
                .orderByAsc(ViewButton::getOrderNo).list();

    }

    @Override
    protected void copyPropertyHandle(ViewButton entity, String... value) {
        entity.setView(value[0]);
    }
}
