package tech.abc.platform.scheduler.enums;

import lombok.Getter;

/**
 * 任务状态
 *
 * @author wqliu
 * @date 2020-03-30 21:17
 */
@Getter
public enum JobStatusEnum {

    /**
     * 运行
     */
    RUNNING,
    /**
     * 暂停
     */
    PAUSE,
    ;

}
