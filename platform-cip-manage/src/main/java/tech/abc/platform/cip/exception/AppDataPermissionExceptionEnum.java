package tech.abc.platform.cip.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 数据角色权限相关异常
 *
 * @author wqliu
 * @date 2021-10-22
 */

@Getter
public enum AppDataPermissionExceptionEnum implements ExceptionInterface {

    /**
     * 已存在同数据角色和业务编码完全一致的应用
     */
    DATA_ROLE_AND_BUSINESS_CODE_EXIST("已存在同数据角色和业务编码完全一致的应用"),

    ;
    private String message;

    AppDataPermissionExceptionEnum(String message) {
        this.message = message;
    }

}
