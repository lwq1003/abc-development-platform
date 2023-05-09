package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 实体视图 实体类
 *
 * @author wqliu
 * @date 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_entity_view")
public class EntityView extends BaseEntity {

    /**
     * 视图类型
     */
    @TableField("entity_view_type")
    private String entityViewType;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 主视图
     */
    @TableField("main_view_flag")
    private String mainViewFlag;

    /**
     * 主参照视图
     */
    @TableField("main_reference_view_flag")
    private String mainReferenceViewFlag;


    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 初始化前
     */
    @TableField("before_init")
    private String beforeInit;

    /**
     * 初始化后
     */
    @TableField("after_init")
    private String afterInit;

    /**
     * 验证数据
     */
    @TableField("validateData")
    private String validateData;

    /**
     * 保存前
     */
    @TableField("before_save")
    private String beforeSave;

    /**
     * 保存后
     */
    @TableField("after_save")
    private String afterSave;

    /**
     * 通用参数变更
     */
    @TableField("common_param_change")
    private String commonParamChange;

    /**
     * 树路径
     */
    @TableField("tree_path")
    private String treePath;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 实体模型
     */
    @TableField("entity_model")
    private String entityModel;

    /**
     * 实体
     */
    @TableField("entity")
    private String entity;

}
