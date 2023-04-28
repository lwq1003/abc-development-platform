package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 实体模型属性 视图对象类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EntityModelPropertyVO extends BaseVO {
    /**
     * 实体模型
     */
    @NotBlank(message = "实体模型不能为空")
    private String entityModel;

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
     * 数据类型
     */
    @NotBlank(message = "数据类型不能为空")
    private String dataType;

    /**
     * 字典类型
     */
    private String dictionaryType;

    /**
     * 控件类型
     */
    @NotBlank(message = "控件类型不能为空")
    private String widgetType;

    /**
     * 显示格式
     */
    private String formatPattern;

    /**
     * 是否可为空
     */
    private String nullFlag;

    /**
     * 最大长度
     */
    private Integer maxLength;

    /**
     * 小数位数
     */
    private Integer decimalLength;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否唯一
     */
    @NotBlank(message = "是否唯一不能为空")
    private String uniqueFlag;

    /**
     * 唯一性参照
     */
    private String entityModelProperty;

    /**
     * 是否主属性
     */
    @NotBlank(message = "是否主属性不能为空")
    private String mainFlag;

    /**
     * 是否上级属性
     */
    @NotBlank(message = "【是否上级属性】不能为空")
    private String parentPropertyFlag;

    /**
     * 排序
     */
    private String orderNo;

    /**
     * 备注
     */
    private String remark;


    /********字典类*****/
    /**
     * 数据类型
     */
    private String dataTypeName;
    /**
     * 是否可为空
     */
    private String nullFlagName;
    /**
     * 是否唯一
     */
    private String uniqueFlagName;
    /**
     * 是否主属性
     */
    private String mainFlagName;

    /**
     * 是否上级属性
     */

    private String parentPropertyFlagName;


    /********实体类*****/
    /**
     * 唯一性参照
     */
    private String entityModelPropertyName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
