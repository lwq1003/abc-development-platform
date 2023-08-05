package tech.abc.platform.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程监听器
 * @author wqliu
 * @date 2020-07-13
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_listener")

public class WorkflowListener extends BaseEntity {

    private static final long serialVersionUID = 1L;


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
     * 排序号
     */
    @TableField("ORDER_NO")
    private String orderNo;


}
