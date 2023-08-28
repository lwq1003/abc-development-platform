package tech.abc.platform.support.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import tech.abc.platform.support.entity.PortletParam;
import tech.abc.platform.support.mapper.PortletParamMapper;
import tech.abc.platform.support.service.PortletParamService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
/**
* 组件参数 服务实现类
*
* @author wqliu
* @date 2023-06-02
*/
@Service
@Slf4j
public class PortletParamServiceImpl extends BaseServiceImpl<PortletParamMapper, PortletParam> implements PortletParamService {
    @Override
    public PortletParam init() {
        PortletParam entity=new PortletParam();
        //默认值处理
        entity.setValue("");
        return entity;
    }

    @Override
    public void beforeAdd(PortletParam entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(PortletParam::getName, entity.getName())
            .eq(PortletParam::getPortlet, entity.getPortlet()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
            }
        }
        //验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(PortletParam::getCode, entity.getCode())
            .eq(PortletParam::getPortlet, entity.getPortlet()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【编码】");
            }
        }
    }

    @Override
    public void beforeModify(PortletParam entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(PortletParam::getName, entity.getName())
                .eq(PortletParam::getPortlet, entity.getPortlet())
                .ne(PortletParam::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
            }
        }
        //验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(PortletParam::getCode, entity.getCode())
                .eq(PortletParam::getPortlet, entity.getPortlet())
                .ne(PortletParam::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<PortletParam> list = this.lambdaQuery().in(PortletParam::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    public List<PortletParam> getByPortletId(String portletId) {
        return this.lambdaQuery().eq(PortletParam::getPortlet,portletId).list();
    }

    @Override
    protected void copyPropertyHandle(PortletParam entity, String... value) {
        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置关联的组件标识
            entity.setPortlet(value[0]);
        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }

}

