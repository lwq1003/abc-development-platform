package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 实体视图 视图对象类
*
* @author wqliu
* @date 2023-12-24
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EntityViewVO extends BaseVO {
    /**
    * 视图类型
    */
    @NotBlank(message = "【视图类型】不能为空")
    private String entityViewType;

    /**
    * 实体模型
    */
    @NotBlank(message = "【实体模型】不能为空")
    private String entityModel;

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
    * 主参照视图
    */
    @NotBlank(message = "【主参照视图】不能为空")
    private String mainReferenceViewFlag;

    /**
    * 主视图
    */
    @NotBlank(message = "【主视图】不能为空")
    private String mainViewFlag;

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
    * 启用高级配置
    */
    private String enableAdvanceConfig;

    /**
    * 高级配置规则
    */
    private String advanceConfigRule;

    /**
    * 高级配置选项
    */
    private String advanceConfigOption;

    /**
    * 备注
    */
    private String remark;

    /**
    * 实体
    */
    @NotBlank(message = "【实体】不能为空")
    private String entity;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 视图类型
    */
    private String entityViewTypeName;

    /**
    * 主参照视图
    */
    private String mainReferenceViewFlagName;

    /**
    * 主视图
    */
    private String mainViewFlagName;

    /**
    * 启用高级配置
    */
    private String enableAdvanceConfigName;


    /********实体类、用户单选、组织机构单选*****/
    /**
    * 实体模型
    */
    private String entityModelName;


    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
