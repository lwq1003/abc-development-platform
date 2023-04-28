package tech.abc.platform.common.exception;

import lombok.Getter;

/**
 * 文件异常类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Getter
public enum FileException implements ExceptionInterface {
    /**
     * 请上传文件
     */
    UPLOAD_IS_NULL("请上传文件"),
    /**
     * 文件处理失败
     */
    HANDLE_ERROR("文件处理失败:{0}"),
    /**
     * 数据导入失败
     */
    EXCEL_IMPORT_FAILURE("数据导入失败:第{0}行 {1}"),
    /**
     * 下载失败
     */
    DOWNLOAD_FAILURE("下载失败:{0}"),
    /**
     * 数据导出失败
     */
    EXCEL_EXPORT_FAILURE("数据导出失败:{0}"),
    /**
     * 磁盘文件复制失败
     */
    COPY_FAILURE("磁盘文件复制失败：{0}"),
    /**
     * 读取文件内容失败
     */
    READ_FAILURE("读取文件内容失败：{0}"),

    ;


    private String message;

    FileException(String message) {
        this.message = message;
    }

}