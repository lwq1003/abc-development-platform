package tech.abc.platform.system.vo;


import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 字典项 视图对象类
*
* @author wqliu
* @date 2023-04-26
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictionaryItemVO extends BaseVO {
    /**
    * 字典类型
    */
    @NotBlank(message = "【字典类型】不能为空")
    private String dictionaryType;

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
    * 状态
    */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
    * 排序
    */
    private String orderNo;


    /********字典类*****/
    /**
    * 状态
    */
    private String statusName;

    /********实体类*****/
    /**
    * 字典类型
    */
    private String dictionaryTypeName;

    /********范围查询*****/

    /********自定义扩展*****/
    /**
    * 忽略上级
    */
    private Boolean ignoreParent;

    /********子对象*****/




}
