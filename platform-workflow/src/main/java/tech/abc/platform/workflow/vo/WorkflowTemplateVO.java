package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 流程模板 视图对象类
*
* @author wqliu
* @date 2023-07-06
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowTemplateVO extends BaseVO {
    /**
    * 类别
    */
    @NotBlank(message = "【类别】不能为空")
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
    * 模板版本
    */
    private String templateVersion;

    /**
    * 状态
    */
    private String status;

    /**
    * 模型
    */
    private String model;


    /**
     * 排序
     */
    private String orderNo;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 类别
    */
    private String categoryName;

    /**
    * 状态
    */
    private String statusName;




    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/
    private String tempDefinitionId;

    public String getTempDefinitionId(){
        return code+":"+templateVersion;
    }


    /********子对象*****/




}
