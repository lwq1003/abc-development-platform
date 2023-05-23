package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 组件参数 视图对象
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="组件参数对象")
public class PortletParamVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组件标识")
    private String portletId;

    @ApiModelProperty(value = "名称")
    private String paramName;

    @ApiModelProperty(value = "编码")
    private String paramCode;

    @ApiModelProperty(value = "值")
    private String paramValue;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/

    /********字典类*****/

    /********子对象*****/



}
