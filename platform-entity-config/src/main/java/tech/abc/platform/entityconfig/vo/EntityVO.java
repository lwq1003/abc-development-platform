package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 实体 视图对象类
 *
 * @author wqliu
 * @date 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EntityVO extends BaseVO {
    /**
     * 模块
     */
    @NotBlank(message = "模块不能为空")
    private String module;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空")
    private String code;

    /**
     * 作者
     */
    @NotBlank(message = "作者不能为空")
    private String author;

    /**
     * 排序
     */
    private String orderNo;

    /**
     * 备注
     */
    private String remark;


    /********字典类*****/

    /********实体类*****/
    /**
     * 模块
     */
    private String moduleName;

    /********范围查询*****/

    /********自定义扩展*****/
    

    /********子对象*****/

}
