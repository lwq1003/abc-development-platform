package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息 视图对象
 *
 * @author wqliu
 * */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@ApiModel(value = "消息对象")
public class MessageVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息类型")
    private String type;

    @ApiModelProperty(value = "接收人")
    private String receiver;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;


    @ApiModelProperty(value = "是否已读")
    private String readFlag;


    /********自定义扩展*****/

    /********字典类*****/
    @ApiModelProperty(value = "是否已读")
    private String readFlagName;
    /********子对象*****/


}
