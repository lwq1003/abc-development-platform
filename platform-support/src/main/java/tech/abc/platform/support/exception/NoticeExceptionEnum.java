package tech.abc.platform.support.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 通知公告相关异常
 * @author wqliu
 * @date: 2020-03-29 19:11
 *
 */
@Getter
public enum NoticeExceptionEnum implements ExceptionInterface {

    /**
     * 通知公告不存在
     */
    NOTICE_NOT_EXIST("通知公告不存在"),

    ;


    private String message;
    NoticeExceptionEnum(String message){
        this.message = message;
    }

}
