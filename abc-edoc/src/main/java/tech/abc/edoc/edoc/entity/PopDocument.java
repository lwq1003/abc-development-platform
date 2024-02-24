package tech.abc.edoc.edoc.entity;

import lombok.Data;
import tech.abc.platform.common.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 文件夹与文件混合对象
 *
 * @author wqliu
 * @date 2021-03-07
 */
@Data
public class PopDocument extends BaseEntity {



    // region 公用属性

    /**
     * 上级标识
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;


    /**
     * 对象类型 FOLDER 文件夹 DOCUMENT 文档
     */
    private String objectType;

    //endregion

    // region 文件夹专有属性

    //endregion


    // region 文档专有属性
    /**
     * 大小
     */
    private String size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 阅读次数
     */
    private Integer readCount;

    /**
     * 下载次数
     */
    private Integer downloadCount;


    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 锁定人标识
     */
    private String lockId;

    /**
     * 锁定时间
     */
    private LocalDateTime lockTime;

    //endregion


}
