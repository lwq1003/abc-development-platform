package tech.abc.platform.common.vo;

import lombok.Data;

/**
 * 列表项视图对象
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class ListItemVO {
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
}
