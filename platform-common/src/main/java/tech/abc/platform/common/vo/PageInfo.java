package tech.abc.platform.common.vo;

import lombok.Data;

/**
 * 分页信息
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class PageInfo {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 每页记录数
     */
    private Integer firstDataIndex;

    public Integer getFirstDataIndex() {
        return (pageNum - 1) * pageSize;
    }
}
