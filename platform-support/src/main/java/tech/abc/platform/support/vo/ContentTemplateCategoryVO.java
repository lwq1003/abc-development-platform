package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 内容模板分类 视图对象
 *
 * @author wqliu
 * */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "内容模板分类对象")
public class ContentTemplateCategoryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父标识")
    private String parentId;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序号")
    private String orderNo;
    /********自定义扩展*****/
    @ApiModelProperty(value = "父分类")
    private String parentName;

    @ApiModelProperty(value = "忽略上级")
    private Boolean ignoreParent;
    /********字典类*****/

    /********子对象*****/


}
