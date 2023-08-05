package tech.abc.platform.workflow.entity;

import lombok.Data;

import java.util.List;

/**
 * 流程节点
 *
 * @author wqliu
 * @date 2023-07-12
 */
@Data
public class MyFlowNode {
    /**
     * 标识
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 子节点
     */
    private MyFlowNode child;

    /**
     * 节点配置
     */
    private String config;

    /**
     * 节点配置
     */
    private List<MyFlowNode> branchList;
}
