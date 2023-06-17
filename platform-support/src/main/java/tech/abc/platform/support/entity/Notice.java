package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知公告 实体类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_notice")
public class Notice extends BaseEntity {

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 是否重要
     */
    @TableField("important_flag")
    private String importantFlag;

    /**
     * 是否置顶
     */
    @TableField("top_flag")
    private String topFlag;

    /**
     * 允许评论
     */
    @TableField("comment_flag")
    private String commentFlag;

    /**
     * 阅读次数
     */
    @TableField("read_count")
    private Integer readCount;

    /**
     * 发布人
     */
    @TableField("publisher")
    private String publisher;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /********非库表存储属性*****/
    /**
     * 发布范围
     */
    @TableField(exist = false)
    private List<String> publishScope;
}
