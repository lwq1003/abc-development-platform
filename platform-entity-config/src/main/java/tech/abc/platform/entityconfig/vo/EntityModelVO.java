package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 实体模型 视图对象类
*
* @author wqliu
* @date 2023-04-19
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EntityModelVO extends BaseVO {
    /**
    * 父级模型
    */
    @NotBlank(message = "【父级模型】不能为空")
    private String parentModel;

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
    * 是否主模型
    */
    @NotBlank(message = "【是否主模型】不能为空")
    private String mainModelFlag;

    /**
    * 是否自关联
    */
    @NotBlank(message = "【是否自关联】不能为空")
    private String selfReferenceFlag;

    /**
    * 排序
    */
    private String orderNo;

    /**
    * 备注
    */
    private String remark;

    /**
    * 实体
    */
    @NotBlank(message = "【实体】不能为空")
    private String entity;


    /********字典类*****/
    /**
    * 父级模型
    */
    private String parentModelName;
    /**
    * 是否主模型
    */
    private String mainModelFlagName;
    /**
    * 是否自关联
    */
    private String selfReferenceFlagName;

    /********实体类*****/
    /**
     * 实体
     */
    private String entityName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
