package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 通知接收 视图对象类
*
* @author wqliu
* @date 2023-06-15
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class NoticeReceiverVO extends BaseVO {
    /**
    * 通知通告
    */
    @NotBlank(message = "【通知通告】不能为空")
    private String notice;

    /**
    * 组织机构
    */
    @NotBlank(message = "【组织机构】不能为空")
    private String organization;


    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
