package tech.abc.edoc.edoc.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.framework.config.DateConverterConfig;

/**
 * 文档 实体类
 *
 * @author wqliu
 * @date 2024-02-05
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ed_document")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "document")
public class Document extends BaseEntity {

    /**
    * 文件夹
    */
    @TableField("parent_id")
    @Field(index = false,type = FieldType.Keyword)
    private String parentId;

    /**
    * 名称
    */
    @TableField("name")
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;

    /**
    * 长度
    */
    @TableField("length")
    @Field(index = false,type = FieldType.Long)
    private Long length;

    /**
    * 大小
    */
    @TableField("size")
    @Field(index = false,type = FieldType.Keyword)
    private String size;

    /**
    * 类型
    */
    @TableField("type")
    @Field(index = false,type = FieldType.Keyword)
    private String type;

    /**
    * 保存名称
    */
    @TableField("real_name")
    @Field(index = false,type = FieldType.Keyword)
    private String realName;

    /**
    * 路径
    */
    @TableField("path")
    @Field(index = false,type = FieldType.Keyword)
    private String path;

    /**
    * 阅读量
    */
    @TableField("read_count")
    @Field(index = false,type=FieldType.Integer)
    private Integer readCount;

    /**
    * 下载量
    */
    @TableField("download_count")
    @Field(index = false,type=FieldType.Integer)
    private Integer downloadCount;

    /**
    * 状态
    */
    @TableField("status")
    @Field(index = false,type = FieldType.Keyword)
    private String status;

    /**
    * 上传时间
    */
    @TableField("upload_time")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

    /**
    * 锁定人
    */
    @TableField(value="lock_id",updateStrategy= FieldStrategy.IGNORED)
    @Field(index = false,type = FieldType.Keyword)
    private String lockId;

    /**
    * 锁定时间
    */
    @TableField(value="lock_time",updateStrategy= FieldStrategy.IGNORED)
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime lockTime;

    /********非库表存储属性*****/
    /**
     * 内容
     */
    @TableField(exist = false)
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;
}
