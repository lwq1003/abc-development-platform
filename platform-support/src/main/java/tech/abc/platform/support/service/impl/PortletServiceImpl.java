package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.constant.TableFieldConstant;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.Portlet;
import tech.abc.platform.support.entity.PortletParam;
import tech.abc.platform.support.mapper.PortletMapper;
import tech.abc.platform.support.service.PortletParamService;
import tech.abc.platform.support.service.PortletService;

import java.util.List;

/**
 * 组件 服务实现类
 *
 * @author wqliu
 */
@Service
public class PortletServiceImpl extends BaseServiceImpl<PortletMapper, Portlet> implements PortletService {

    @Autowired
    private PortletParamService portletParamService;

    @Override
    public Portlet init() {
        Portlet entity = new Portlet();
        entity.setStatus(StatusEnum.NORMAL.name());
        return entity;
    }

    @Override
    public void beforeAdd(Portlet entity) {
        // 验证名称同目录下唯一
        if (this.lambdaQuery().eq(Portlet::getName, entity.getName())
                .eq(Portlet::getCategoryId, entity.getCategoryId())
                .count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }

        // 验证编码全局唯一
        if (this.lambdaQuery().eq(Portlet::getCode, entity.getCode()).count() > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    @Override
    public void beforeModify(Portlet entity) {
        // 验证名称同目录下唯一
        if (this.lambdaQuery().eq(Portlet::getName, entity.getName())
                .eq(Portlet::getCategoryId, entity.getCategoryId())
                .ne(Portlet::getId, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.NAME_EXIST);
        }

        // 验证编码全局唯一
        if (this.lambdaQuery().eq(Portlet::getCode, entity.getCode())
                .ne(Portlet::getId, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.CODE_EXIST);
        }
    }


    @Override
    public void afterRemove(Portlet entity) {
        removeParam(entity);
    }


    @Override
    public void enable(String id) {
        Portlet entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        Portlet entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    public void afterAddOrModifyOp(Portlet entity) {
        removeParam(entity);
        // 插入参数表
        if (CollectionUtils.isNotEmpty(entity.getParamList())) {
            entity.getParamList().stream().forEach(param -> {
                // 设置父节点
                param.setPortletId(entity.getId());
                param.setId(null);
                portletParamService.add(param);
            });
        }

    }


    @Override
    public void afterQuery(Portlet entity) {
        attachChild(entity);
    }

 
    public void attachChild(Portlet entity) {
        // 构造查询条件
        QueryWrapper<PortletParam> queryWrapper = new QueryWrapper<>();
        // 父节点id
        queryWrapper.lambda().eq(PortletParam::getPortletId, entity.getId());
        queryWrapper.orderByAsc(TableFieldConstant.DEFAULT_SORT_FILED);
        List<PortletParam> list = portletParamService.list(queryWrapper);
        entity.setParamList(list);
    }

    /**
     * 删除参数
     *
     * @param entity
     */
    private void removeParam(Portlet entity) {
        //  清理子表
        QueryWrapper<PortletParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PortletParam::getPortletId, entity.getId());
        portletParamService.remove(queryWrapper);
    }


    /**
     * 数据不为空检查，检查通过后返回对象
     *
     * @param id
     * @return
     */
    public Portlet getEntity(String id) {
        // 标识非空判断
        if (id == null) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        // 对象非空判断
        Portlet entity = query(id);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

}
