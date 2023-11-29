package tech.abc.platform.oss.entity;

import lombok.Data;
import tech.abc.platform.common.base.BaseVO;

/**
 * 文件 实体
 * 匹配前端simple-uploader控件
 *
 * @author wqliu
 * @date 2023-11-27
 */
@Data
public class FileInfo  {

    /**
     * 文件标识
     */
    private String identifier;
    /**
     * 文件名
     */
    private String filename;

    /**
     * 模块编码
     */
    private String moduleCode;

    /**
     * 实体类型
     */
    private String entityType;

    /**
     * 实体标识
     */
    private String entityId;

    /**
     * 文件类型
     */
    private String type;


    /**
     * 总大小
     */
    private Long totalSize;


}
