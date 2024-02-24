package tech.abc.edoc.edoc.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 文档收藏夹 视图对象类
*
* @author wqliu
* @date 2024-02-04
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentFavoriteVO extends BaseVO {
    /**
    * 对象类型
    */
    @NotBlank(message = "【对象类型】不能为空")
    private String objectType;

    /**
    * 对象标识
    */
    @NotBlank(message = "【对象标识】不能为空")
    private String objectId;

    /**
    * 用户标识
    */
    private String userId;


    /********非库表存储属性*****/
    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 大小
     */
    private String size;


    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
