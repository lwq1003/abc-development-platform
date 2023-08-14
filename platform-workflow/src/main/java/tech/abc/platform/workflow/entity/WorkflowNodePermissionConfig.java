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
 * 工作流节点权限配置
 * 工作流程环节权限配置 实体类
 *
 * @author wqliu
 * @date 2023-08-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_node_permission_config")
public class WorkflowNodePermissionConfig extends BaseEntity {

    /**
    * 流程定义
    */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
    * 环节标识
    */
    @TableField("node_id")
    private String nodeId;

    /**
    * 区域编码
    */
    @TableField("area_code")
    private String areaCode;

    /**
    * 区域名称
    */
    @TableField("area_name")
    private String areaName;

    /**
    * 权限
    */
    @TableField("permission")
    private String permission;

    /********非库表存储属性*****/
}
