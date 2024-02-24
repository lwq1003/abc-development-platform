package tech.abc.edoc.edoc.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 文档收藏异常
 *
 * @author wqliu
 * @date: 2020-03-29 19:11
 */
@Getter
public enum DocumentFavoriteExceptionEnum implements ExceptionInterface {


    FAVORITE_ITEM_EXIST("收藏项已存在，无需再次添加"),
    CANT_REMOVE_OTHER_DATA("只能移除自己的收藏项"),

    ;
    private String message;

    DocumentFavoriteExceptionEnum(String message) {
        this.message = message;
    }

}
