package tech.abc.platform.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程环节配置 实体类
 *
 * @author wqliu
 * @date 2023-07-16
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_node_config")
public class WorkflowNodeConfig extends BaseEntity {

    /**
     * 流程定义
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
    * 环节
    */
    @TableField("node_id")
    private String nodeId;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 模式
    */
    @TableField("mode")
    private String mode;

    /**
    * 指定处理人
    */
    @TableField("set_assignee_flag")
    private String setAssigneeFlag;

    /**
    * 用户组
    */
    @TableField("user_group")
    private String userGroup;


    /**
     * 用户组名称
     */
    @TableField("user_group_name")
    private String userGroupName;

    /********非库表存储属性*****/
}
