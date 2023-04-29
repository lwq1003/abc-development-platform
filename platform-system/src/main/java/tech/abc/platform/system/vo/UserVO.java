package tech.abc.platform.system.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户 视图对象类
 *
 * @author wqliu
 * @date 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserVO extends BaseVO {
    /**
     * 组织机构
     */
    @NotBlank(message = "【组织机构】不能为空")
    private String organization;

    /**
     * 姓名
     */
    @NotBlank(message = "【姓名】不能为空")
    private String name;

    /**
     * 账号
     */
    @NotBlank(message = "【账号】不能为空")
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    @NotBlank(message = "【性别】不能为空")
    private String gender;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态
     */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
     * 强制修改密码
     */
    @NotBlank(message = "【强制修改密码】不能为空")
    private String forceChangePasswordFlag;

    /**
     * 失败登录次数
     */
    private Integer failLoginCount;

    /**
     * 锁定时间
     */
    private LocalDateTime lockTime;

    /**
     * 排序
     */
    private String orderNo;


    /********字典类*****/
    /**
     * 性别
     */
    private String genderName;
    /**
     * 状态
     */
    private String statusName;
    /**
     * 强制修改密码
     */
    private String forceChangePasswordFlagName;

    /********实体类*****/
    /**
     * 组织机构
     */
    private String organizationName;

    /********范围查询*****/

    /********自定义扩展*****/
    /**
     * 忽略上级
     */
    private Boolean ignoreParent;

    /**
     * 忽略用户组
     */
    private Boolean ignoreUserGroup;

    /**
     * 令牌
     */
    private String token;

    /********子对象*****/

    /**
     * 按钮权限数组
     */

    private List<String> buttonPermission;


    /**
     * 菜单权限数组
     */

    private List<MenuTreeVO> menuPermission;
}


