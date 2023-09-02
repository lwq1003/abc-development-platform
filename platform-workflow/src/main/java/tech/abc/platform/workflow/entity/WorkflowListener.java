package tech.abc.platform.workflow.entity;

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
 * 工作流监听器 实体类
 *
 * @author wqliu
 * @date 2023-08-29
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_listener")
public class WorkflowListener extends BaseEntity {

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 类别
    */
    @TableField("category")
    private String category;

    /**
    * 类型
    */
    @TableField("type")
    private String type;

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
    * 排序
    */
    @TableField("order_no")
    private String orderNo;

    /********非库表存储属性*****/
}
