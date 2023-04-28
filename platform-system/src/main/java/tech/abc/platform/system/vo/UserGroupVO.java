package tech.abc.platform.system.vo;


import tech.abc.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 用户组 视图对象类
 *
 * @author wqliu
 * @date 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroupVO extends BaseVO {
    /**
     * 上级
     */
    @NotBlank(message = "【上级】不能为空")
    private String userGroup;

    /**
     * 名称
     */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
     * 编码
     */

    private String code;

    /**
     * 状态
     */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
     * 排序
     */
    private String orderNo;

    /**
     * 备注
     */
    private String remark;


    /********字典类*****/
    /**
     * 状态
     */
    private String statusName;

    /********实体类*****/
    /**
     * 上级
     */
    private String userGroupName;

    /********范围查询*****/

    /********自定义扩展*****/
    /**
     * 忽略上级
     */
    private Boolean ignoreParent;

    /********子对象*****/


}
