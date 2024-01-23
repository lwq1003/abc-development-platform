package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>
import java.time.LocalDateTime;

/**
 * ${table.comment!} 实体类
 *
 * @author ${author}
 * @date ${date}
 *
 */
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
<#--启用链式编程-->
@Accessors(chain = true)
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass} {
<#else>
public class ${entity} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list databaseStoreEntityModelPropertyList as field>
    <#if field.name!?length gt 0>
    /**
    * ${field.name}
    */
    </#if>
    @TableField("${field.code?uncap_first?replace("([A-Z])", "_$1", "r")?lower_case}")
    private ${field.propertyDataType} ${field.code};

</#list>
<#------------  END 字段循环遍历  ---------->
    /********非库表存储属性*****/
<#list noDatabaseStoreEntityModelPropertyList as field>
    <#if field.name!?length gt 0>
    /**
    * ${field.name}
    */
    </#if>
    @TableField(exist = false)
    private ${field.propertyDataType} ${field.code};

</#list>
}
