package tech.abc.platform.cip.vo;


import tech.abc.platform.common.base.BaseMapVO;
import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 消息权限 视图对象类
*
* @author wqliu
* @date 2023-05-03
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MessagePermissionVO extends BaseMapVO {
    /**
    * 应用
    */
    @NotBlank(message = "【应用】不能为空")
    private String app;

    /**
    * 消息主题
    */
    @NotBlank(message = "【消息主题】不能为空")
    private String messageTopic;


    /********字典类*****/

    /********实体类*****/
    /**
    * 应用
    */
    private String appName;
    /**
    * 消息主题
    */
    private String messageTopicName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
