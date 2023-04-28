package tech.abc.platform.common.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security框架要求的用户类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class MyUserDetails implements UserDetails {


    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthority;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    // 用户属性

    private String userId;
    private String organizationId;
    private String organizationName;
    private String name;
    private String moduleId;
    private String departmentId;
    private String companyId;
    private String groupId;
    private String parkId;
    /**
     * 组织机构全称（带路径）
     */
    private String organizationFullName;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
