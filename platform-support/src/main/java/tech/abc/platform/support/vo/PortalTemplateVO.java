package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 门户模板 视图对象
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="门户模板对象")
public class PortalTemplateVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "门户设置")
    private String config;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/
    @ApiModelProperty(value = "状态名称")
    private String statusName;
    /********字典类*****/

    /********子对象*****/



}
