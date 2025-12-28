package tech.abc.platform.system.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 权限项相关异常
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Getter
public enum PermissionItemExceptionEnum implements ExceptionInterface {


    /**
     * 该权限项当前停用
     */
    STATUS_IS_DEAD("该权限项当前停用"),
    /**
     * 权限项不存在
     */
    NOT_EXIST("权限项不存在"),
    /**
     * 同级下已存在相同名称的权限项目
     */
    PERMISSION_NAME_EXIST("同级下已存在相同名称的权限项目"),
    /**
     * 权限项被分配角色，不能删除
     */
    PERMISSION_HAS_ROLE("权限项被分配角色，不能删除"),
    /**
     * 无权访问，请联系系统管理员
     */
    NO_PERMISSION("无权访问，请联系系统管理员"),
    /**
     * 外部链接不能为空
     */
    EXTERNAL_LINK_REQUIRED("外部链接不能为空"),
    /**
     * 组件不能为空
     */
    COMPONENT_REQUIRED("组件不能为空"),
    ;


    private String message;

    PermissionItemExceptionEnum(String message) {
        this.message = message;
    }

}
