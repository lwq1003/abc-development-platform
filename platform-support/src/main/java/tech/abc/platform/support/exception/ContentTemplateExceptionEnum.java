package tech.abc.platform.support.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 内容模板相关异常
 * @author wqliu
 * @date: 2020-03-29 19:11
 *
 */
@Getter
public enum ContentTemplateExceptionEnum implements ExceptionInterface {

    /**
     * 存在内容模板，不能删除
     */
    HAS_CHILDREN("存在内容模板，不能删除"),
    /**
     * 内容模板不存在
     */
    NOT_EXIST("内容模板不存在"),
    /**
     * 内容模板处理失败
     */
    OPERATION_FAILED("内容模板处理失败"),
    ;


    private String message;
    ContentTemplateExceptionEnum(String message){
        this.message = message;
    }

}
