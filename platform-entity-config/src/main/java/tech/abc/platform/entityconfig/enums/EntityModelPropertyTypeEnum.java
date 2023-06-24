package tech.abc.platform.entityconfig.enums;

import lombok.Getter;


/**
 * 实体模型属性枚举类型
 *
 * @author wqliu
 * @date 2022/11/07
 */
@Getter
public enum EntityModelPropertyTypeEnum {


    /**
     * 文本
     */
    STRING,
    /**
     * 整数
     */
    INTEGER,
    /**
     * 长整数
     */
    LONG,
    /**
     * 浮点数
     */
    DOUBLE,
    /**
     * 日期时间
     */
    DATETIME,
    /**
     * 金额
     */
    DECIMAL,
    /**
     * 数据字典
     */
    DATA_DICTIONARY,
    /**
     * 实体
     */
    ENTITY,
    /**
     * 用户单选
     */
    USER_SINGLE,
    /**
     * 组织机构单选
     */
    ORGANIZATION_SINGLE,
    /**
     * 组织机构多选
     */
    ORGANIZATION_MULTIPLE,
    /**
     * 图标
     */
    ICON,
    /**
     * 附件
     */
    ATTACHMENT,
    /**
     * 流水号
     */
    SERIAL_NO


}
