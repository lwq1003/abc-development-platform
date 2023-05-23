package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 高级查询方案 视图对象
 *
 * @author wqliu
 * */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "高级查询方案对象")
public class AdvanceQuerySchemeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "实体编码")
    private String entityCode;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/

    /********字典类*****/

    /********子对象*****/


}
