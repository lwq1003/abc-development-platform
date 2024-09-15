package tech.abc.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 数据筛选规则
 *
 * @author wqliu
 * @date 2024-08-06
 */
@Data
public class DataFilterRuleVO {

    /**
     * 数据筛选组集合
     */
    private List<DataFilterGroupVO> filters;

    /**
     * 逻辑操作
     */
    private String logicalOperator;
}
