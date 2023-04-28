package tech.abc.platform.common.utils;

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
    public static MyUserDetails getCurrentUser() {
        MyUserDetails myUserDetails = null;

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                myUserDetails = (MyUserDetails) principal;
            } else {
                // 获取到为匿名用户时，同样抛出异常
                throw new SessionExpiredException("会话失效");
            }
        } catch (Exception e) {
            // 未获取到凭证,抛出会话超时异常
            throw new SessionExpiredException("会话失效");
        }
        return myUserDetails;

    }

    /**
     * 获取用户id
     */
    public static String getId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getUserId();
        }
        return "";
    }

    /**
     * 获取账号
     */
    public static String getAccount() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getUsername();
        }
        return "";
    }

    /**
     * 获取用户姓名
     */
    public static String getName() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getName();
        }
        return "";
    }

    /**
     * 获取用户所在组织机构标识
     */
    public static String getOrganizationId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getOrganizationId();
        }
        return "";
    }

    /**
     * 获取用户所在组织机构名称
     */
    public static String getOrganizationName() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getOrganizationName();
        }
        return "";
    }


    /**
     * 获取用户所在模块/工序ID
     */
    public static String getModuleId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getModuleId();
        }
        return "";
    }

    /**
     * 获取用户所在部门ID
     * 如有多级部门，只取直接上级部门
     */
    public static String getDepartmentId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getDepartmentId();
        }
        return "";
    }

    /**
     * 获取用户所在公司ID
     */
    public static String getCompanyId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getCompanyId();
        }
        return "";
    }

    /**
     * 获取用户所在集团
     */
    public static String getGroupId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getGroupId();
        }
        return "";
    }

    /**
     * 获取用户所在园区
     */
    public static String getParkId() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            return myUserDetails.getParkId();
        }
        return "";
    }

    /**
     * 获取用户权限
     */
    public static List<MyGrantedAuthority> getGrantedAuthority() {
        MyUserDetails myUserDetails = getCurrentUser();
        if (myUserDetails != null) {
            if (null != myUserDetails.getGrantedAuthority()) {
                return (List<MyGrantedAuthority>) myUserDetails.getGrantedAuthority();
            }
        }
        return null;
    }


}
