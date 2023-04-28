package tech.abc.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 图表 视图对象
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class ChartVO<T> {
    private List<String> columns;

    private List<T> rows;
}
