package tech.abc.platform.entityconfig.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

/**
 * 实体视图 视图对象类
 *
 * @author wqliu
 * @date 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EntityViewVO extends BaseVO {
    /**
     * 视图类型
     */
    @NotBlank(message = "视图类型不能为空")
    private String entityViewType;

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
     * 主视图
     */

    private String mainViewFlag;

    /**
     * 主参照视图
     */

    private String mainReferenceViewFlag;

    /**
     * 排序
     */
    private String orderNo;

    /**
     * 初始化前
     */
    private String beforeInit;

    /**
     * 初始化后
     */
    private String afterInit;

    /**
     * 验证数据
     */
    private String validateData;

    /**
     * 保存前
     */
    private String beforeSave;

    /**
     * 保存后
     */
    private String afterSave;

    /**
     * 通用参数变更
     */
    private String commonParamChange;

    /**
     * 树路径
     */
    private String treePath;

    /**
     * 备注
     */
    private String remark;

    /**
     * 实体模型
     */
    @NotBlank(message = "实体模型不能为空")
    private String entityModel;

    /**
     * 实体
     */
    @NotBlank(message = "实体不能为空")
    private String entity;


    /********字典类*****/
    /**
     * 视图类型
     */
    private String entityViewTypeName;

    /**
     * 主视图
     */

    private String mainViewFlagName;


    /**
     * 主参照视图
     */

    private String mainReferenceViewFlagName;

    /********实体类*****/
    /**
     * 实体模型
     */
    private String entityModelName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
