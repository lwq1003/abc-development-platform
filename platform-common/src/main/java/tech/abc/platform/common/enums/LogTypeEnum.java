package tech.abc.platform.common.enums;

import lombok.Getter;

/**
 * 系统日志类型
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum LogTypeEnum {
    /**
     * 操作日志
     */
    OPERATION,
    /**
     * 审计日志
     */
    AUDIT,
    /**
     * 调度日志
     */
    SCHEDULER;

}
