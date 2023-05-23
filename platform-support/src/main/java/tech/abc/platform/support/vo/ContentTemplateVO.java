package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 内容模板 视图对象
 *
 * @author wqliu
 * */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "内容模板对象")
public class ContentTemplateVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "编码")
    @NotBlank(message = "编码不能为空")
    private String code;

    @ApiModelProperty(value = "类型")
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "模板分类标识")
    @NotBlank(message = "分类不能为空")
    private String categoryId;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/
    @ApiModelProperty(value = "模板分类")
    private String categoryName;

    @ApiModelProperty(value = "忽略上级")
    private Boolean ignoreParent;
    /********字典类*****/
    @ApiModelProperty(value = "类型")
    private String typeName;
    /********子对象*****/


}
