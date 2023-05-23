package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 组件 视图对象
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="组件对象")
public class PortletVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别标识")
    @NotBlank(message="类别不能为空")
    private String categoryId;

    @ApiModelProperty(value = "名称")
    @NotBlank(message="名称不能为空")
    private String name;

    @ApiModelProperty(value = "编码")
    @NotBlank(message="编码不能为空")
    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/

    /********字典类*****/
    @ApiModelProperty(value = "状态名称")
    private String statusName;

     @ApiModelProperty(value = "类别名称")
     private String categoryName ;
    /********子对象*****/
    @ApiModelProperty(value = "参数")
    private List<PortletParamVO> paramList;

}
