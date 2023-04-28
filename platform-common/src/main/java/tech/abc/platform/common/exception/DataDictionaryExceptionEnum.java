package tech.abc.platform.common.exception;

import lombok.Getter;

/**
 * 数据字典相关异常
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum DataDictionaryExceptionEnum implements ExceptionInterface {

    /**
     * 未找到字典项对应的编码
     */
    CANT_FIND_DICTIONARY_ITEM_CODE("未找到字典项【{0}】对应的编码"),
    ;


    private String message;

    DataDictionaryExceptionEnum(String message) {
        this.message = message;
    }

}
