package tech.abc.platform.common.vo;

import lombok.Data;

/**
 * 排序
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class SortInfo {
    /**
     * 降序常量
     */
    private static final String DESC = "desc";
    /**
     * element ui框架定义的降序常量
     */
    private static final String DESC_DEFINITION = "descending";
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则（升序、降序）
     */
    private String sortType;

    /**
     * 是否为升序
     */
    private boolean ascType;

    /**
     * 判断是否按升序排列
     *
     * @return true 是 false 否
     */
    public boolean getAscType() {
        // 只要未明确按降序排列，则一律按升序处理
        if (sortType != null) {
            if (DESC.equals(sortType) || DESC_DEFINITION.equals(sortType)) {
                return false;
            }
        }
        return true;
    }
}
