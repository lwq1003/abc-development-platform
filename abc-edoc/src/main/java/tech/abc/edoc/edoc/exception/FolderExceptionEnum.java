package tech.abc.edoc.edoc.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 文件夹相关异常
 *
 * @author wqliu
 * @date: 2020-03-29 19:11
 */
@Getter
public enum FolderExceptionEnum implements ExceptionInterface {


    CANT_SELECT_CHILDREN("不能选择当前文件夹的子文件夹")


    ;
    private String message;

    FolderExceptionEnum(String message) {
        this.message = message;
    }

}
