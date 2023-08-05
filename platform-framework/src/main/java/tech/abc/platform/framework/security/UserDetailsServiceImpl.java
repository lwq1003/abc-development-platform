package tech.abc.platform.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.entity.MyGrantedAuthority;
import tech.abc.platform.common.entity.MyUserDetails;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.enums.OrganizationTypeEnum;
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
        User user = userService.getOne(userQueryWrapper.lambda().eq(User::getAccount, account));
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
        userDetails.setUserId(userId);
        // 设置账号是否已停用
        userDetails.setEnabled(!user.getStatus().equals(UserStatusEnum.DEAD.toString()));
        // 设置账号是否已锁定
        userDetails.setAccountNonLocked(!user.getStatus().equals(UserStatusEnum.LOCK.toString()));

        // 填充用户对象常用属性
        fillMyUserDetails(userDetails, user);
        return userDetails;
    }

    /**
     * 填充用户对象常用属性
     */
    private void fillMyUserDetails(MyUserDetails userDetails, User user) {
        // TODO：基于JWT令牌模式，每次请求都会填充如下信息，进行大量数据库查询操作，性能低，考虑使用redis缓存


        userDetails.setUserId(user.getId());
        userDetails.setName(user.getName());
        userDetails.setOrganizationId(user.getOrganization());
        userDetails.setOrganizationName(organizationService.getById(user.getOrganization()).getName());
        // 逐级查找组织机构类型
        List<Organization> list = organizationService.list();
        String currentOrganizationId = user.getOrganization();
        StringBuilder organizationFullName = new StringBuilder();

        do {
            // 取当前组织机构
            Organization currentOrganization = null;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(currentOrganizationId)) {
                    currentOrganization = list.get(i);
                    break;
                }
            }
            organizationFullName.insert(0, currentOrganization.getName()).insert(0, "/");
            String organizationType = currentOrganization.getType();
            if (StringUtils.isNotBlank(organizationType)) {
                OrganizationTypeEnum organizationTypeEnum = OrganizationTypeEnum.valueOf(organizationType);
                switch (organizationTypeEnum) {
                    case MODULE:
                        userDetails.setModuleId(currentOrganizationId);
                        break;
                    case DEPARTMENT:
                        // 存在多级部门时，只取直接上级部门，忽略高层级部门
                        if (StringUtils.isBlank(userDetails.getDepartmentId())) {
                            userDetails.setDepartmentId(currentOrganizationId);
                        }
                        break;
                    case COMPANY:
                        userDetails.setCompanyId(currentOrganizationId);
                        break;
                    case GROUP:
                        userDetails.setGroupId(currentOrganizationId);
                        break;
                    default:
                        break;
                }

            }
            // 指向上级
            currentOrganizationId = currentOrganization.getOrganization();
        }
        while (!currentOrganizationId.equals(TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID));

        userDetails.setOrganizationFullName(organizationFullName.toString());
    }


}
