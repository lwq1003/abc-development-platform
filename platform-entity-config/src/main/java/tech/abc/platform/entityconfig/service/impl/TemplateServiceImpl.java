package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.entityconfig.entity.Template;
import tech.abc.platform.entityconfig.mapper.TemplateMapper;
import tech.abc.platform.entityconfig.service.TemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板 服务实现类
 *
 * @author wqliu
 * @date 2024-09-10
 */
@Service
@Slf4j
public class TemplateServiceImpl extends BaseServiceImpl<TemplateMapper, Template> implements TemplateService {

    @Override
    public Template init() {
        Template entity = new Template();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());

        return entity;
    }

    @Override
    public void beforeAdd(Template entity) {
        // 唯一性验证

    }

    @Override
    public void beforeModify(Template entity) {
        // 唯一性验证
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

