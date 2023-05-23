package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 单据流水号 视图对象
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="单据流水号对象")
public class SerialNoVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单据编码")
    private String code;

    @ApiModelProperty(value = "单据名称")
    private String name;

    @ApiModelProperty(value = "前缀")
    private String prefix;

    @ApiModelProperty(value = "日期")
    private String datePart;

    @ApiModelProperty(value = "流水号长度")
    private Integer length;

    @ApiModelProperty(value = "连接符")
    private String connectors;

    @ApiModelProperty(value = "当前流水号")
    private Integer serialNo;

    @ApiModelProperty(value = "重置策略")
    private String resetStrategy;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/

    /********字典类*****/
    @ApiModelProperty(value = "重置策略")
    private String resetStrategyName;
    /********子对象*****/



}
