package tech.abc.platform.common.vo;

import lombok.Data;

/**
 * 分页查询视图模型
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class PageQueryVO<T> {

    /**
     * 分页信息
     */
    private PageInfo page;

    /**
     * 排序信息
     */
    private SortInfo sort;

    /**
     * 实体视图模型
     */
    private T entity;
}
