package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程权限配置 视图对象
 *
 * @author wqliu
 * @date 2020-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "流程权限配置对象")
public class WorkflowRightConfigVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程定义标识")
    private String processDefinitionId;

    @ApiModelProperty(value = "环节编码")
    private String definitionKey;

    @ApiModelProperty(value = "环节名称")
    private String name;

    @ApiModelProperty(value = "资源标识")
    private String resourceId;

    @ApiModelProperty(value = "资源编码")
    private String resourceCode;

    @ApiModelProperty(value = "是否可见")
    private String visibleFlag;

    @ApiModelProperty(value = "是否只读")
    private String readonlyFlag;


    /********自定义扩展*****/

    /********字典类*****/

    /********子对象*****/


}
