package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.Portlet;
import tech.abc.platform.support.entity.PortletCategory;
import tech.abc.platform.support.mapper.PortletCategoryMapper;
import tech.abc.platform.support.service.PortletCategoryService;
import tech.abc.platform.support.service.PortletService;

import java.util.List;

/**
 * 组件类别 服务实现类
 *
 * @author wqliu
 */
@Service
public class PortletCategoryServiceImpl extends BaseServiceImpl<PortletCategoryMapper, PortletCategory> implements PortletCategoryService {

    @Autowired
    private PortletService portletService;


    @Override
    public PortletCategory init() {
        PortletCategory entity = new PortletCategory();
        entity.setStatus(StatusEnum.NORMAL.name());
        return entity;
    }

    @Override
    public void beforeAdd(PortletCategory entity) {
        // 验证名称唯一
        if (this.lambdaQuery().eq(PortletCategory::getName, entity.getName()).count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }
    }


    @Override
    public void beforeModify(PortletCategory entity) {
        // 验证名称唯一
        if (this.lambdaQuery().eq(PortletCategory::getName, entity.getName())
                .ne(PortletCategory::getId, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }
    }


    @Override
    public void beforeRemove(PortletCategory entity) {
        // 删除组件
        QueryWrapper<Portlet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Portlet::getCategoryId, entity.getId());
        portletService.remove(queryWrapper);
    }


    @Override
    public void enable(String id) {
        PortletCategory entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        PortletCategory entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    public List<PortletCategory> getPortletList(QueryWrapper<PortletCategory> queryWrapper) {
        // 获取所有的状态正常的组件类别
        List<PortletCategory> portletCategoryList = list(queryWrapper);


        // 填充组件
        if (CollectionUtils.isNotEmpty(portletCategoryList)) {
            portletCategoryList.stream().forEach(portletCategory -> {

                List<Portlet> portletList = portletService.lambdaQuery()
                        .eq(Portlet::getCategoryId, portletCategory.getId())
                        .eq(Portlet::getStatus, StatusEnum.NORMAL.name())
                        .orderByAsc(Portlet::getOrderNo).list();
                for (Portlet portlet : portletList) {
                    // portletService.attachChild(portlet);
                }

                portletCategory.setPortletList(portletList);
            });
        }
        return portletCategoryList;
    }


    /**
     * 数据不为空检查，检查通过后返回对象
     *
     * @param id
     * @return
     */
    public PortletCategory getEntity(String id) {
        // 标识非空判断
        if (id == null) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        // 对象非空判断
        PortletCategory entity = query(id);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

}
