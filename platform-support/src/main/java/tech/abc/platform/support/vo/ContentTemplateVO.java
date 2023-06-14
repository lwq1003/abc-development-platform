package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 内容模板 视图对象类
*
* @author wqliu
* @date 2023-05-31
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ContentTemplateVO extends BaseVO {
    /**
    * 分类
    */
    @NotBlank(message = "【分类】不能为空")
    private String category;

    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
    * 编码
    */
    @NotBlank(message = "【编码】不能为空")
    private String code;

    /**
    * 类型
    */
    @NotBlank(message = "【类型】不能为空")
    private String type;

    /**
    * 内容
    */
    private String content;

    /**
    * 排序
    */
    private String orderNo;

    /**
    * 备注
    */
    private String remark;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 分类
    */
    private String categoryName;

    /**
    * 类型
    */
    private String typeName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
