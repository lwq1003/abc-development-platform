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
<#list entityModelPropertyList as field>
    <#if field.name!?length gt 0>
    /**
    * ${field.name}
    */
    </#if>
    <#if field.nullFlag=="NO" && field.propertyDataType=="String">
    @NotBlank(message = "【${field.name}】不能为空")
    </#if>
    private ${field.propertyDataType} ${field.code};

</#list>
<#------------  END 字段循环遍历  ---------->

    /********非库表存储属性*****/
<#list noDatabaseStoreEntityModelPropertyList as field>
    /**
    * ${field.name}
    */
    private ${field.propertyDataType} ${field.code};

</#list>



    /********字典类*****/
<#list entityModelPropertyList as item>
    <#if item.dataType=="DATA_DICTIONARY">
    /**
    * ${item.name}
    */
    private String ${item.code}Name;

    </#if>
</#list>
<#list noDatabaseStoreEntityModelPropertyList as item>
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
