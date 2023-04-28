package tech.abc.platform.entityconfig.codegenerator.entity;

import lombok.Data;

/**
 * 表字段信息
 * @author wqliu
 * @date 2022-11-8
 */
@Data
public class TableFieldInfo {
    /**
     * 代码
     */
    private String code;
    /**
     * 名字
     */
    private String name;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 是否可为空 YES 是 NO 否
     */
    private String nullFlag;
    /**
     * 默认值
     */
    private String defaultValue;

}
