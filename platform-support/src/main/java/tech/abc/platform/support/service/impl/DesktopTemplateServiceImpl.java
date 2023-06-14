package tech.abc.platform.support.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.support.entity.DesktopTemplate;
import tech.abc.platform.support.mapper.DesktopTemplateMapper;
import tech.abc.platform.support.service.DesktopTemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 桌面模板 服务实现类
 *
 * @author wqliu
 * @date 2023-06-02
 */
@Service
@Slf4j
public class DesktopTemplateServiceImpl extends BaseServiceImpl<DesktopTemplateMapper, DesktopTemplate> implements DesktopTemplateService {
    @Override
    public DesktopTemplate init() {
        DesktopTemplate entity = new DesktopTemplate();
        // 默认值处理
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(DesktopTemplate entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DesktopTemplate::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DesktopTemplate::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(DesktopTemplate entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DesktopTemplate::getName, entity.getName())
                    .ne(DesktopTemplate::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DesktopTemplate::getCode, entity.getCode())
                    .ne(DesktopTemplate::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DesktopTemplate> list = this.lambdaQuery().in(DesktopTemplate::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(DesktopTemplate entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }


    @Override
    public void enable(String id) {
        DesktopTemplate entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        DesktopTemplate entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }


}

