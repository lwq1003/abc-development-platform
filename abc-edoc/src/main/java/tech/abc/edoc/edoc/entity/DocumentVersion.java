package tech.abc.edoc.edoc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 文档版本 实体类
 *
 * @author wqliu
 * @date 2024-02-04
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ed_document_version")
public class DocumentVersion extends BaseEntity {

    /**
    * 文档标识
    */
    @TableField("document_id")
    private String documentId;

    /**
    * 文档版本
    */
    @TableField("document_version")
    private String documentVersion;

    /**
    * 版本标记
    */
    @TableField("version_tag")
    private String versionTag;

    /**
    * 路径
    */
    @TableField("path")
    private String path;

    /********非库表存储属性*****/
}
