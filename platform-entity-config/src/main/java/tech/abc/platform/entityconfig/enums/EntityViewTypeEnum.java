package tech.abc.platform.entityconfig.enums;

import lombok.Getter;


/**
 * 实体模型属性枚举类型
 *
 * @author wqliu
 * @date 2022/11/07
 */
@Getter
public enum EntityViewTypeEnum {


    /**
     * 列表
     */
    LIST,
    /**
     * 新增
     */
    ADD,
    /**
     * 修改
     */
    MODIFY,
    /**
     * 查看
     */
    VIEW,
    /**
     * 参照
     */
    REFERENCE,
    /**
     * 树
     */
    TREE,

    /**
     * 树表
     */
    TREELIST,
    /**
     * 树参照
     */
    TREEREFERENCE,
    /**
     * 树多选参照
     */
    TREEMULTIPLEREFERENCE,
    /**
     * 自定义
     */
    CUSTOM


}
