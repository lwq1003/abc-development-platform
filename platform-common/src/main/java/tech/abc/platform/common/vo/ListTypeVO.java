package tech.abc.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 列表类型视图对象
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class ListTypeVO {
    /**
     * 标识
     */
    private String value;
    /**
     * 名称
     */
    private String label;

    /**
     * 编码
     */
    private String code;

    /**
     * 列表项
     */
    private List<ListItemVO> items;
}
