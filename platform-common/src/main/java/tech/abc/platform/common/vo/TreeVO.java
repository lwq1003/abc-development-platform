package tech.abc.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 树视图模型
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class TreeVO {
    private String id;
    private String code;
    private String label;
    private Boolean expand;
    private Boolean checked;
    private List<TreeVO> children;
    private String parentId;

}
