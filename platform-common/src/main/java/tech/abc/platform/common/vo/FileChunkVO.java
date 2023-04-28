package tech.abc.platform.common.vo;

import tech.abc.platform.common.base.BaseVO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件块对象模型，匹配前端simple-uploader控件
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class FileChunkVO extends BaseVO {


    /**
     * 当前文件块编号，从1开始
     */
    private Integer chunkNumber;
    /**
     * 分块大小
     */
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    private Long currentChunkSize;
    /**
     * 总大小
     */
    private Long totalSize;
    /**
     * 文件标识
     */
    private String identifier;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 总块数
     */
    private Integer totalChunks;
    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件块内容
     */
    private MultipartFile file;

    /**
     * 业务分类
     */
    private String entityType;

    /**
     * 业务实体标识
     */
    private String entityId;

    /**
     * 模块编码
     */
    private String moduleCode;

}
