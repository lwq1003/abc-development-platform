package tech.abc.platform.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.abc.platform.common.entity.MyGrantedAuthority;
import tech.abc.platform.common.entity.MyUserDetails;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.enums.UserStatusEnum;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security框架自定义用户服务类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public UserDetails loadUserByUsername(String account) {


        // 登录系统时，对用户名大小写不敏感
        account = account.toLowerCase();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = userService.getByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        String userId = user.getId();


        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setUsername(account);
        userDetails.setPassword(user.getPassword());
        // 权限置空
        List<MyGrantedAuthority> grantedAuthorities = new ArrayList<>();
        userDetails.setGrantedAuthority(grantedAuthorities);

        // 设置账号是否已停用
        userDetails.setEnabled(!user.getStatus().equals(UserStatusEnum.DEAD.toString()));
        // 设置账号是否已锁定
        userDetails.setAccountNonLocked(!user.getStatus().equals(UserStatusEnum.LOCK.toString()));


        return userDetails;
    }


}
