package tech.abc.platform.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 处理意见 实体类
 *
 * @author wqliu
 * @date 2023-07-25
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_comment")
public class WorkflowComment extends BaseEntity {

    /**
    * 流程实例
    */
    @TableField("process_instance_id")
    private String processInstanceId;

    /**
    * 环节名称
    */
    @TableField("node_name")
    private String nodeName;

    /**
    * 处理人
    */
    @TableField("assignee")
    private String assignee;

    /**
    * 处理人姓名
    */
    @TableField("assignee_name")
    private String assigneeName;

    /**
    * 处理意见
    */
    @TableField("comment")
    private String comment;

    /**
    * 提交类型
    */
    @TableField("commit_type")
    private String commitType;

    /**
    * 提交时间
    */
    @TableField("commit_time")
    private LocalDateTime commitTime;

    /********非库表存储属性*****/
}
