package tech.abc.platform.common.component.easyexcel;


import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * excel异常枚举
 *
 * @author wqliu
 * @date 2023-05-17
 */
@Getter
public enum ExcelExceptionEnum implements ExceptionInterface {


    /**
     * 导出方法未实现
     */
    EXPORT_METHOD_UNIMPLEMENTED("导出方法未实现"),


    ;
    private String message;

    ExcelExceptionEnum(String message) {
        this.message = message;
    }

}
