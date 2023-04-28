package tech.abc.platform.system.enums;

import lombok.Getter;

/**
 * 流水号重置策略
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Getter
public enum SerialNoResetStrategyEnum {
    /**
     * 每年
     */
    YEAR,
    /**
     * 每月
     */
    MONTH,
    /**
     * 每天
     */
    DAY;


}
