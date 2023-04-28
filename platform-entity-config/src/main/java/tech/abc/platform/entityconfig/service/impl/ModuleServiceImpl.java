package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.Entity;
import tech.abc.platform.entityconfig.entity.Module;
import tech.abc.platform.entityconfig.mapper.ModuleMapper;
import tech.abc.platform.entityconfig.service.EntityService;
import tech.abc.platform.entityconfig.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* 模块 服务实现类
*
* @author wqliu
* @date 2023-04-09
*/
@Service
@Slf4j
public class ModuleServiceImpl extends BaseServiceImpl<ModuleMapper, Module> implements ModuleService {

    @Autowired
    private EntityService entityService;

    @Override
    public Module init() {
        Module entity=new Module();
        //默认值处理
        entity.setApp("");
        entity.setRemark("");
        return entity;
    }

    @Override
    public void beforeAdd(Module entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Module::getName, entity.getName())
            .eq(Module::getApp, entity.getApp()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
        }
        //验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(Module::getCode, entity.getCode())
            .eq(Module::getApp, entity.getApp()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【编码】");
        }
        //验证 缩略码 同节点下唯一
        long countAbbreviation = this.lambdaQuery().eq(Module::getAbbreviation, entity.getAbbreviation())
            .eq(Module::getApp, entity.getApp()).count();
        if (countAbbreviation > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【缩略码】");
        }
    }

    @Override
    public void beforeModify(Module entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Module::getName, entity.getName())
            .eq(Module::getApp, entity.getApp())
            .ne(Module::getId, entity.getId()).count();
        if (countName > 0) {
        throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
        }
        //验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(Module::getCode, entity.getCode())
            .eq(Module::getApp, entity.getApp())
            .ne(Module::getId, entity.getId()).count();
        if (countCode > 0) {
        throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【编码】");
        }

    }

    @Override
    public void beforeRemove(Module entity) {
        // 判断该模块下是否有实体
        LambdaQueryChainWrapper<Entity> queryWrapper = entityService.lambdaQuery().eq(Entity::getModule, entity.getId());
        long count = queryWrapper.count();
        if (count > 0) {
            // 先清除下属的实体
            entityService.remove(queryWrapper);
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Module> list = this.lambdaQuery().in(Module::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

}

