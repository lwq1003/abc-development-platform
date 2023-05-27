package tech.abc.platform.notification.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 系统消息 视图对象类
*
* @author wqliu
* @date 2023-05-27
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemMessageVO extends BaseVO {
    /**
    * 类型
    */
    @NotBlank(message = "【类型】不能为空")
    private String type;

    /**
    * 接收人
    */
    @NotBlank(message = "【接收人】不能为空")
    private String receiver;

    /**
    * 标题
    */
    @NotBlank(message = "【标题】不能为空")
    private String title;

    /**
    * 内容
    */
    private String content;

    /**
    * 是否已读
    */
    private String readFlag;



    /********非库表存储属性*****/


    /********字典类*****/
    /**
    * 类型
    */
    private String typeName;

    /**
    * 是否已读
    */
    private String readFlagName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
