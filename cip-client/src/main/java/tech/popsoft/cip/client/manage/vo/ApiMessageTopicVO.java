package tech.popsoft.cip.client.manage.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;


/**
 * 消息主题 视图对象
 *
 * @author wqliu
 * @date 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "消息主题对象")
public class ApiMessageTopicVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "处理器")
    private String handler;

    @ApiModelProperty(value = "发送器")
    private String sender;

    @ApiModelProperty(value = "响应主题编码")
    private String responseTopicCode;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/
    @ApiModelProperty(value = "应用标识")
    private String appId;

    @ApiModelProperty(value = "是否已授权")
    private String hasPermission;

    @ApiModelProperty(value = "是否已订阅")
    private String hasSubscription;
    /********字典类*****/
    @ApiModelProperty(value = "状态")
    private String statusName;

    @ApiModelProperty(value = "分类")
    private String categoryName;

    @ApiModelProperty(value = "是否已授权")
    private String hasPermissionName;

    @ApiModelProperty(value = "是否已订阅")
    private String hasSubscriptionName;

    /********子对象*****/


}
