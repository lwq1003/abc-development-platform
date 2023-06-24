package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
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
import tech.abc.platform.entityconfig.service.EntityModelService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
/**
* ${table.comment!} 服务实现类
*
* @author ${author}
* @date ${date}
*/
@Service
@Slf4j
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
<#list entityModelPropertyList as item>
    <#if item.dataType=="SERIAL_NO">
    @Autowired
    private EntityModelService entityModelService;
    </#if>
</#list>

    @Override
    public ${entity} init() {
        ${entity} entity=new ${entity}();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        <#list entityModelPropertyList as item>
            <#if item.defaultValue??>
                <#if item.dataType=="STRING" || item.dataType=="DATA_DICTIONARY">
        entity.set${item.code?cap_first}("${item.defaultValue}");
                <#elseif item.dataType=="INTEGER" || item.dataType=="LONG" || item.dataType=="DOUBLE" >
        entity.set${item.code?cap_first}(${item.defaultValue});
                <#elseif  item.dataType=="DECIMAL">
        entity.set${item.code?cap_first}(new BigDecimal("${item.defaultValue}"));
                </#if>
            </#if>
        </#list>
        return entity;
    }

    @Override
    public void beforeAdd(${entity} entity) {
        //唯一性验证
        <#list entityModelPropertyList as item>
            <#if item.uniqueFlag=="YES">
                <#if item.entityModelProperty?? ==false || item.entityModelProperty=="">
        //验证 ${item.name} 全局唯一
        if (StringUtils.isNotBlank(entity.get${item.code?cap_first}())) {
            long count${item.code?cap_first} = this.lambdaQuery().eq(${entity}::get${item.code?cap_first}, entity.get${item.code?cap_first}()).count();
            if (count${item.code?cap_first} > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【${item.name}】");
            }
        }
                <#else>
        //验证 ${item.name} 同节点下唯一
        if (StringUtils.isNotBlank(entity.get${item.code?cap_first}())) {
            long count${item.code?cap_first} = this.lambdaQuery().eq(${entity}::get${item.code?cap_first}, entity.get${item.code?cap_first}())
            .eq(${entity}::get${item.entityModelProperty?cap_first}, entity.get${item.entityModelProperty?cap_first}()).count();
            if (count${item.code?cap_first} > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【${item.name}】");
            }
        }
                </#if>
            </#if>
        </#list>

<#list entityModelPropertyList as item>
    <#if item.dataType=="SERIAL_NO">
        //自动生成流水号
        entity.setSerialNo(entityModelService.generateSerialNo("${entity}"));
    </#if>
</#list>
    }

    @Override
    public void beforeModify(${entity} entity) {
        //唯一性验证
    <#list entityModelPropertyList as item>
        <#if item.uniqueFlag=="YES">
            <#if item.entityModelProperty?? ==false || item.entityModelProperty=="">
        //验证 ${item.name} 全局唯一
        if (StringUtils.isNotBlank(entity.get${item.code?cap_first}())) {
            long count${item.code?cap_first} = this.lambdaQuery().eq(${entity}::get${item.code?cap_first}, entity.get${item.code?cap_first}())
                .ne(${entity}::getId, entity.getId()).count();
            if (count${item.code?cap_first} > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【${item.name}】");
            }
        }
            <#else>
        //验证 ${item.name} 同节点下唯一
        if (StringUtils.isNotBlank(entity.get${item.code?cap_first}())) {
            long count${item.code?cap_first} = this.lambdaQuery().eq(${entity}::get${item.code?cap_first}, entity.get${item.code?cap_first}())
                .eq(${entity}::get${item.entityModelProperty?cap_first}, entity.get${item.entityModelProperty?cap_first}())
                .ne(${entity}::getId, entity.getId()).count();
            if (count${item.code?cap_first} > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【${item.name}】");
            }
        }
            </#if>
        </#if>
    </#list>
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<${entity}> list = this.lambdaQuery().in(${entity}::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.get${mainPropertyCode?cap_first}());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(${entity} entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.set${mainPropertyCode?cap_first} (entity.get${mainPropertyCode?cap_first}() + " 副本");
    }

}

