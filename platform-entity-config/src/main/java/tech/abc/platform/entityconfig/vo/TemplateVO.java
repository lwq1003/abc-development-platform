package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 模板 视图对象类
*
* @author wqliu
* @date 2024-09-10
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TemplateVO extends BaseVO {
    /**
    * 名称
    */
    private String name;

    /**
    * 描述
    */
    private String content;

    /**
    * 组织机构
    */
    private String organizaiton;

    /**
    * 时分秒
    */
    private String time1;

    /**
    * 时分
    */
    private String time2;

    /**
    * 时
    */
    private String time3;

    /**
    * 年月日
    */
    private LocalDateTime date1;

    /**
    * 年月日时分
    */
    private LocalDateTime day2;

    /**
    * 年月日时分秒
    */
    private LocalDateTime day3;


    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/
    /**
    * 组织机构
    */
    private String organizaitonName;


    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
