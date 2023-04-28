package tech.abc.platform.system.enums;

import lombok.Getter;

/**
 * 用户状态
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL,
    /**
     * 停用
     */
    DEAD,
    /**
     * 锁定
     */
    LOCK,
    ;

}
