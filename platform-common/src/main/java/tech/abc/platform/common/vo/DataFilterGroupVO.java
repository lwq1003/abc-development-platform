package tech.abc.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 数据筛选组
 *
 * @author wqliu
 * @date 2024-08-06
 */
@Data
public class DataFilterGroupVO {

    /**
     * 数据筛选条件集合
     */
    private List<DataFilterConditionVO> conditions;

    /**
     * 逻辑操作
     */
    private String logicalOperator;
}
