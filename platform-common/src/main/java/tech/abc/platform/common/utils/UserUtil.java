package tech.abc.platform.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.common.entity.MyGrantedAuthority;
import tech.abc.platform.common.entity.MyUserDetails;
import tech.abc.platform.common.exception.SessionExpiredException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 用户工具类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Slf4j
@UtilityClass
public final class UserUtil {

    /**
     * 获取当前用户
     */
    public static JSONObject getCurrentUser() {
        // 从SpringSecurity中获取当前用户
        MyUserDetails myUserDetails = null;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                myUserDetails = (MyUserDetails) principal;
            } else {
                // 获取到为匿名用户时，抛出异常
                throw new SessionExpiredException("会话失效");
            }
        } catch (Exception e) {
            // 未获取到凭证,抛出会话超时异常
            throw new SessionExpiredException("会话失效");
        }
        // 获取账号
        String account=myUserDetails.getUsername();
        // 从缓存中获取用户
        CacheUtil cacheUtil=SpringUtil.getBean(CacheUtil.class);
        String userJson=cacheUtil.get(account);
        if(StringUtils.isNotEmpty(userJson)){
            return JSON.parseObject(userJson);
        }else{
            // 缓存无记录,抛出会话超时异常
            throw new SessionExpiredException("会话失效");
        }

    }

    /**
     * 获取用户id
     */
    public static String getId() {
        JSONObject user = getCurrentUser();
        return user.getString("id");
    }

    /**
     * 获取账号
     */
    public static String getAccount() {
        JSONObject user = getCurrentUser();
        return user.getString("account");
    }

    /**
     * 获取用户姓名
     */
    public static String getName() {
        JSONObject user = getCurrentUser();
        return user.getString("name");
    }

    /**
     * 获取用户所在组织机构标识
     */
    public static String getOrganizationId() {
        JSONObject user = getCurrentUser();
        return user.getString("organization");
    }

    /**
     * 获取用户所在组织机构名称
     */
    public static String getOrganizationName() {
        JSONObject user = getCurrentUser();
        return user.getString("organizationName");
    }


    /**
     * 获取用户所在模块标识
     */
    public static String getModuleId() {
        JSONObject user = getCurrentUser();
        return user.getString("moduleId");
    }

    /**
     * 获取用户所在部门标识
     * 如有多级部门，只取直接上级部门
     */
    public static String getDepartmentId() {
        JSONObject user = getCurrentUser();
        return user.getString("departmentId");
    }

    /**
     * 获取用户所在公司标识
     */
    public static String getCompanyId() {
        JSONObject user = getCurrentUser();
        return user.getString("companyId");
    }

    /**
     * 获取用户所在集团标识
     */
    public static String getGroupId() {
        JSONObject user = getCurrentUser();
        return user.getString("groupId");
    }


}
