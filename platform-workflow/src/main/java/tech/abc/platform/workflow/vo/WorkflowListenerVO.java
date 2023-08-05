package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程监听器 视图对象
 * @author wqliu
 * @date 2020-07-13
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="流程监听器对象")
public class WorkflowListenerVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private String status;


     @ApiModelProperty(value = "排序号")
     private String orderNo;


    /********自定义扩展*****/

    /********字典类*****/
    @ApiModelProperty(value = "类别名称")
    private String categoryName;

     @ApiModelProperty(value = "类型名称")
     private String typeName;

     @ApiModelProperty(value = "状态名称")
     private String statusName;

    /********子对象*****/



}
