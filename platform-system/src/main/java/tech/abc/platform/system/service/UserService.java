package tech.abc.platform.system.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户 服务接口类
 *
 * @author wqliu
 * @date 2023-04-23
 */
public interface UserService extends BaseService<User> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);
   
    /**
     * 启用
     *
     * @param id 标识
     */
    void enable(String id);

    /**
     * 停用
     *
     * @param id 标识
     */
    void disable(String id);

    /**
     * 重置密码
     *
     * @param id 标识
     */
    void resetPassword(String id);

    /**
     * 锁定
     *
     * @param id 标识
     */
    void lock(String id);

    /**
     * 解锁
     *
     * @param id 标识
     */
    void unlock(String id);

    /**
     * 用户修改密码
     *
     * @param account     账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(String account, String oldPassword, String newPassword);

    /**
     * 更新登录失败次数
     *
     * @param id
     */
    void updateLoginFailureCount(String id);

    /**
     * 重置登录失败次数
     *
     * @param id
     */
    void resetLoginFailureCount(String id);

    /**
     * 验证登录是否超出最大尝试次数
     *
     * @param id 用户标识
     * @return 超出重试次数，返回true，否则返回false
     */
    Boolean checkTryCount(String id);


    /**
     * 判断是否超出密码修改时间
     *
     * @param id
     * @return 超出超出密码修改时间，返回true，否则返回false
     */
    boolean checkExceedPasswordChangeDays(String id);


    /**
     * 获取用户权限
     *
     * @param id 用户标识
     * @return 用户权限列表
     */
    List<PermissionItem> getPermission(String id);


    /**
     * 验证权限
     *
     * @param id             用户标识
     * @param permissionCode 权限编码
     */
    void checkPermission(String id, String permissionCode);


    /**
     * 获取姓名
     * @param id 用户标识
     * @return {@link String} 姓名
     */
    String getNameById(String id);
}
