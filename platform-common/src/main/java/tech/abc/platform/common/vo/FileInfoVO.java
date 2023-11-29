package tech.abc.platform.common.vo;

import lombok.Data;
import tech.abc.platform.common.base.BaseVO;

/**
 * 文件视图模型
 * 匹配前端simple-uploader控件
 *
 * @author wqliu
 * @date 2023-11-27
 */
@Data
public class FileInfoVO extends BaseVO {
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
     * 文件类型
     */
    private String type;

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


}
