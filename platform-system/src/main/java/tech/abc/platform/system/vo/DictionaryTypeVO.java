package tech.abc.platform.system.vo;


import tech.abc.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 字典类型 视图对象类
 *
 * @author wqliu
 * @date 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictionaryTypeVO extends BaseVO {
    /**
     * 上级
     */
    @NotBlank(message = "【上级】不能为空")
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
     * 排序
     */
    private String orderNo;


    /********字典类*****/

    /********实体类*****/
    /**
     * 上级
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
