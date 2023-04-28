package ${package.Parent}.vo;


import ${superVoClass};
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
<#if voLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>

/**
* ${table.comment!} 视图对象类
*
* @author ${author}
* @date ${date}
*/
<#if voLombokModel>
@Data
<#if superVoClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
</#if>
<#--启用链式编程-->
@Accessors(chain = true)
</#if>
<#if superVoClass??>
public class ${entity}VO extends BaseVO {
<#else>
public class ${entity} implements Serializable {
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
    * ${field.comment}
    */
    </#if>
    <#if !field.metaInfo.nullable && field.propertyType=="String">
    @NotBlank(message = "【${field.comment}】不能为空")
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->

    /********字典类*****/
<#list entityModelPropertyList as item>
    <#if item.dataType=="DATA_DICTIONARY">
    /**
    * ${item.name}
    */
    private String ${item.code}Name;
    </#if>
</#list>

    /********实体类*****/
<#if entityModelPropertyList?? && (entityModelPropertyList?size > 0)>
<#list entityModelPropertyList as item>
    <#if item.dataType=='ENTITY'>
    /**
    * ${item.name}
    */
    private String ${item.code}Name;
    </#if>
</#list>
</#if>

    /********范围查询*****/
<#if voRangeSet?? && (voRangeSet?size > 0)>
<#list voRangeSet as item>
    /**
    * ${item.name}起
    */
    private String ${item.code}BeginForQuery;

    /**
    * ${item.name}止
    */
    private String ${item.code}EndForQuery;
</#list>
</#if>

    /********自定义扩展*****/
<#if parentPropertyCode??>
    /**
    * 忽略上级
    */
    private Boolean ignoreParent;
</#if>

    /********子对象*****/




}
