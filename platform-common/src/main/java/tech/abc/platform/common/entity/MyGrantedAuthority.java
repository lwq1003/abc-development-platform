package tech.abc.platform.common.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义权限对象
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class MyGrantedAuthority implements GrantedAuthority {

    private String permissionType;
    private String id;
    private String parentId;
    private String code;
    private String pathCode;
    private String name;
    private String icon;
    private String orderNo;


    @Override
    public String getAuthority() {
        return this.pathCode;
    }
}

