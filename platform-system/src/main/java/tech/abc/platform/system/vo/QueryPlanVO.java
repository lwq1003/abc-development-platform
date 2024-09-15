package tech.abc.platform.system.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 查询方案 视图对象类
*
* @author wqliu
* @date 2024-09-04
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class QueryPlanVO extends BaseVO {
    /**
    * 用户标识
    */
    private String userId;

    /**
    * 实体模型编码
    */
    private String entityModelCode;

    /**
    * 名称
    */
    private String name;

    /**
    * 内容
    */
    private String content;


    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
