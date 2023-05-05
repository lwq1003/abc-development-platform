package tech.abc.platform.cip.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

/**
 * 消息主题 视图对象类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MessageTopicVO extends BaseVO {
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
    private String handler;

    /**
     * 发送器
     */
    private String sender;

    /**
     * 响应主题编码
     */
    private String responseTopicCode;

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
     * 是否已授权
     */
    private String hasPermission;


    /**
     * 是否已订阅
     */
    private String hasSubscription;
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


    /**
     * 是否已订阅
     */
    private String hasSubscriptionName;

    /**
     * 应用标识
     */

    private String appId;


    /********子对象*****/


}
