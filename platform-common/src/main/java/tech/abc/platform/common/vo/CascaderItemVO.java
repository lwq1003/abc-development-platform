package tech.abc.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 级联框视图对象
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class CascaderItemVO {
    private String value;
    private String label;
    private List<CascaderItemVO> children;

}
