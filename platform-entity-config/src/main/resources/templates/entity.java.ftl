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
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.name}")
        </#if>
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
     @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
    <#else>
    <#-- 普通字段 -->
    @TableField("${field.name}")
    </#if>
    <#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
    <#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->
}
