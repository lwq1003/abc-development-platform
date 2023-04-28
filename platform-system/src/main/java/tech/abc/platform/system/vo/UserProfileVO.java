package tech.abc.platform.system.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

/**
 * 用户配置 视图对象
 * @author wqliu
 * @date 2020-05-25
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value="用户配置对象")
public class UserProfileVO extends BaseVO
     {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户标识")
    private String userId;

    @ApiModelProperty(value = "桌面配置")
    private String desktopConfig;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "语种")
    private String language;
    /********自定义扩展*****/

    /********字典类*****/

    /********子对象*****/



}
