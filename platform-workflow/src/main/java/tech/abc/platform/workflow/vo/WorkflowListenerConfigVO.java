package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程监听器配置 视图对象
 * @author wqliu
 * @date 2020-10-08
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="流程监听器配置对象")
public class WorkflowListenerConfigVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程定义标识")
    private String processDefinitionId;

    @ApiModelProperty(value = "元素编码")
    private String definitionKey;

    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "类型")
    private String listenerType;

    @ApiModelProperty(value = "监听器名称")
    private String listenerName;

    @ApiModelProperty(value = "监听器编码")
    private String listenerCode;
    /********自定义扩展*****/

    /********字典类*****/
    @ApiModelProperty(value = "类别")
    private String categoryName;

    @ApiModelProperty(value = "事件")
    private String eventName;

    @ApiModelProperty(value = "类型")
    private String listenerTypeName;
    /********子对象*****/



}
