package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.Portlet;
import tech.abc.platform.support.entity.PortletParam;
import tech.abc.platform.support.mapper.PortletMapper;
import tech.abc.platform.support.service.PortletParamService;
import tech.abc.platform.support.service.PortletService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件 服务实现类
 *
 * @author wqliu
 * @date 2023-06-01
 */
@Service
@Slf4j
public class PortletServiceImpl extends BaseServiceImpl<PortletMapper, Portlet> implements PortletService {
    @Autowired
    private PortletParamService portletParamService;

    @Override
    public Portlet init() {
        Portlet entity = new Portlet();
        // 默认值处理
        entity.setCategory("");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(Portlet entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Portlet::getName, entity.getName())
                    .eq(Portlet::getCategory, entity.getCategory()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(Portlet::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(Portlet entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Portlet::getName, entity.getName())
                    .eq(Portlet::getCategory, entity.getCategory())
                    .ne(Portlet::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(Portlet::getCode, entity.getCode())
                    .ne(Portlet::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Portlet> list = this.lambdaQuery().in(Portlet::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Portlet entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
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
    public List<Portlet> getPortletList(QueryWrapper<Portlet> queryWrapper) {
        List<Portlet> list = this.list(queryWrapper);
        for (Portlet item : list) {

            List<PortletParam> paramList = portletParamService.lambdaQuery().eq(PortletParam::getPortlet, item.getId()).list();
            item.setParamList(paramList);
        }
        return list;
    }

    /**
     * 删除参数
     *
     * @param entity
     */
    private void removeParam(Portlet entity) {
        //  清理子表
        QueryWrapper<PortletParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PortletParam::getPortlet, entity.getId());
        portletParamService.remove(queryWrapper);
    }


}

