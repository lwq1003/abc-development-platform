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
    TREE_LIST,
    /**
     * 树参照
     */
    TREE_REFERENCE,
    /**
     * 树表参照
     */
    TREE_LIST_REFERENCE,
    /**
     * 树表多选参照
     */
    TREE_LIST_MULTIPLE_REFERENCE,
    /**
     * 树多选参照
     */
    TREE_MULTIPLE_REFERENCE,
    /**
     * 自定义
     */
    CUSTOM


}
