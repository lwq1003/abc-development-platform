package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 实体模型数据权限 视图对象类
*
* @author wqliu
* @date 2024-08-03
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EntityModelDataPermissionVO extends BaseVO {
    /**
    * 模型标识
    */
    private String modelId;

    /**
    * 表名
    */
    private String tableName;

    /**
    * 规则
    */
    private String rule;

    /**
    * sql片段
    */
    private String sqlPart;

    /**
    * 备注
    */
    private String remark;


    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
