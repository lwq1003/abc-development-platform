package tech.abc.platform.entityconfig.vo;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 专用于数据筛选器的实体模型属性 视图对象类
 *
 * @author wqliu
 * @date 2024-08-05
 */
@Data
@Accessors(chain = true)
public class EntityModelPropertyForFilterVO {
    /**
     * 标签
     */
    private String label;

    /**
     * 编码
     */
    private String value;

    /**
     * 控件类型
     */
    private String renderType;

    /**
     * 操作符
     */
    private String operatorKey;

    /**
     * 数据字典类型编码
     */
    private String dictionaryType;


    /**
     * 集合类型  Organization 组织机构  UserGroup 用户组
     */
    private String collectionType;


    /**
     * 是否多选
     */
    private Boolean multiple;


}
