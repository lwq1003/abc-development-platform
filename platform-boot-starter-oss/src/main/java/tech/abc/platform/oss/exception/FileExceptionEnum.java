package tech.abc.platform.oss.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 文件类异常
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Getter
public enum FileExceptionEnum implements ExceptionInterface {

    /**
     * 文件块不能为空
     */
    FILE_CHUNK_IS_NULL("文件块不能为空"),
    /**
     * 文件块存储出错
     */
    FILE_CHUNK_STORE_ERROR("文件块存储出错"),
    /**
     * 文件块数量不相符，请重新上传
     */
    FILE_CHUNK_NOT_EQUAL("文件块数量不相符，请重新上传"),
    /**
     * 文件块合并出错，请重新上传
     */
    FILE_CHUNK_MERGE_ERROR("文件块合并出错，请重新上传"),
    /**
     * 文件读取出错
     */
    FILE_READ_ERROR("文件读取出错"),
    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_FAILURE("文件下载失败"),
    /**
     * 文件读取失败
     */
    FILE_READ_FAILURE("文件读取失败：{0}"),
    /**
     * 未获取上传文件
     */
    FILE_UPLOAD_NULL("未获取上传文件"),
    /**
     * 文件参数有误
     */
    FILE_ILLEGAL_ARGUMENT("文件参数有误"),
    /**
     * 文件存储出错
     */
    FILE_STORE_ERROR("文件存储出错"),

    ;


    private String message;

    FileExceptionEnum(String message) {
        this.message = message;
    }

}
