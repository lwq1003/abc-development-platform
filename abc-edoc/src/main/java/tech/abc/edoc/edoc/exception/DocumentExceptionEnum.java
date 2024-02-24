package tech.abc.edoc.edoc.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 文档异常
 *
 * @author wqliu
 * @date: 2020-03-29 19:11
 */
@Getter
public enum DocumentExceptionEnum implements ExceptionInterface {


    FOLDER_ID_IS_NULL("查询范围不能为空"),


    ;
    private String message;

    DocumentExceptionEnum(String message) {
        this.message = message;
    }

}
