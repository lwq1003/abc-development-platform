package tech.abc.platform.workflow.enums;

import lombok.Getter;


/**
 * 环节权限编码
 *
 * @author wqliu
 * @date 2023-08-05
 */
@Getter
public enum NodePermissionCodeEnum {

    /**
     * 不可见
     */
    INVISIBLE,
    /**
     * 只读
     */
    READONLY,
    /**
     * 可编辑
     */
    EDITABLE,
    ;

}
