package tech.abc.platform.workflow.entity;

import lombok.Data;

import java.util.List;

/**
 * 条件节点
 *
 * @author wqliu
 * @date 2023-08-02
 */
@Data
public class MyConditionNode {
    /**
     * 表达式
     */
    private String expression;

}
