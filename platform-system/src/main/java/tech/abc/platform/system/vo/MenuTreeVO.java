package tech.abc.platform.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单树
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class MenuTreeVO {

    private String id;
    private String parentId;
    private String path;
    private String name;
    private String component;

    private MetaVO meta;
    private List<MenuTreeVO> children;


}

