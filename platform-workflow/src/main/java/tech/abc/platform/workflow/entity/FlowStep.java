package tech.abc.platform.workflow.entity;

import lombok.Data;

import java.util.List;

/**
 * 流程环节
 * 精简的流程节点信息，仅返回标识、名称和类型
 *
 * @author wqliu
 * @date 2023-07-12
 */
@Data
public class FlowStep {
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


}
