package tech.abc.platform.workflow.vo;

import lombok.Data;

/**
 *  流程节点视图对象
 * @author wqliu
 * @date 2020-6-30
 */
@Data
public class WorkflowNodeVO {
    /**
     * 标识
     */
    private  String id;

    /**
     * 名称
     */
    private  String name;
}
