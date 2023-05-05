package tech.abc.platform.cip.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

/**
 * API服务 视图对象类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ApiServiceVO extends BaseVO {
    /**
     * 分类
     */
    private String category;

    /**
     * 名称
     */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "【编码】不能为空")
    private String code;

    /**
     * 处理器
     */
    @NotBlank(message = "【处理器】不能为空")
    private String handler;

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


    /**
     * 是否已授权
     */
    private String hasPermission;


    /********字典类*****/
    /**
     * 分类
     */
    private String categoryName;
    /**
     * 状态
     */
    private String statusName;

    /**
     * 是否已授权
     */
    private String hasPermissionName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/
    /**
     * 应用标识
     */
    private String appId;

    /********子对象*****/


}
