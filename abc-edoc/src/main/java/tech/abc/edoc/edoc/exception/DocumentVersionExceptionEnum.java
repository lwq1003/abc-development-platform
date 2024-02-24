package tech.abc.edoc.edoc.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 文档版本异常
 *
 * @author wqliu
 * @date: 2020-03-29 19:11
 */
@Getter
public enum DocumentVersionExceptionEnum implements ExceptionInterface {


    VERSION_MUST_GREATER_THAN_NEWEST("新版本号必须高于最新版本号:{0}"),
    VERSION_NOT_FOUND("未找到版本对应的文档"),


    ;
    private String message;

    DocumentVersionExceptionEnum(String message) {
        this.message = message;
    }

}
