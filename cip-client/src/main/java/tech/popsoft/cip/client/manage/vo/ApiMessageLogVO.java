package tech.popsoft.cip.client.manage.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import java.time.LocalDateTime;

/**
 * 消息日志 视图对象
 *
 * @author wqliu
 * @date 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "消息日志对象")
public class ApiMessageLogVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求消息标识")
    private String requestId;

    @ApiModelProperty(value = "请求应用编码")
    private String requestAppCode;

    @ApiModelProperty(value = "请求消息主题编码")
    private String requestTopicCode;

    @ApiModelProperty(value = "请求时间")
    private LocalDateTime requestTime;

    @ApiModelProperty(value = "请求内容")
    private String requestData;

    @ApiModelProperty(value = "响应消息标识")
    private String responseId;

    @ApiModelProperty(value = "响应应用编码")
    private String responseAppCode;

    @ApiModelProperty(value = "响应消息主题编码")
    private String responseTopicCode;

    @ApiModelProperty(value = "响应时间")
    private LocalDateTime responseTime;

    @ApiModelProperty(value = "响应内容")
    private String responseData;

    @ApiModelProperty(value = "响应结果")
    private String responseResult;

    @ApiModelProperty(value = "错误编码")
    private String errorCode;

    @ApiModelProperty(value = "错误信息")
    private String errorMessage;

    @ApiModelProperty(value = "当前状态")
    private String status;

    @ApiModelProperty(value = "发送次数")
    private Integer sendCount;
    /********自定义扩展*****/

    /********字典类*****/
    @ApiModelProperty(value = "消息状态")
    private String responseResultName;

    @ApiModelProperty(value = "响应结果")
    private String statusName;
    /********子对象*****/


}
