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
 * 工作流程环节权限配置 实体类
 *
 * @author wqliu
 * @date 2023-07-26
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_node_permission_config")
public class WorkflowNodePermissionConfig extends BaseEntity {

    /**
    * 流程实例
    */
    @TableField("process_instance_id")
    private String processInstanceId;

    /**
    * 环节标识
    */
    @TableField("node_id")
    private String nodeId;

    /**
    * 环节名称
    */
    @TableField("node_name")
    private String nodeName;

    /**
    * 区域标识
    */
    @TableField("area_id")
    private String areaId;

    /**
    * 处理意见
    */
    @TableField("comment")
    private String comment;

    /**
    * 区域编码
    */
    @TableField("area_code")
    private String areaCode;

    /**
    * 是否可见
    */
    @TableField("visible_flag")
    private String visibleFlag;

    /**
    * 是否只读
    */
    @TableField("readonly_flag")
    private String readonlyFlag;

    /********非库表存储属性*****/
}
