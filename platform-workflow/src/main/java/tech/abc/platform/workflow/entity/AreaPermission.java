package tech.abc.platform.workflow.entity;

import lombok.Data;

/**
 * 区域权限
 * @author wqliu
 * @date 2023-08-08
 */
@Data
public class AreaPermission {

    /**
     * 区域编码
     */
    private String code;

    /**
     * 权限编码
     */
    private String permission;
}
