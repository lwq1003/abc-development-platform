package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 流水号 视图对象类
*
* @author wqliu
* @date 2023-05-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SerialNoVO extends BaseVO {
    /**
    * 模块
    */
    @NotBlank(message = "【模块】不能为空")
    private String module;

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
    * 前缀
    */
    private String prefix;

    /**
    * 日期格式
    */
    private String dateFormat;

    /**
    * 长度
    */
    private Integer length;

    /**
    * 连接符
    */
    private String connector;

    /**
    * 当前值
    */
    private Integer currentValue;

    /**
    * 重置策略
    */
    @NotBlank(message = "【重置策略】不能为空")
    private String resetStrategy;

    /**
    * 排序
    */
    private String orderNo;

    /**
    * 备注
    */
    private String remark;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 重置策略
    */
    private String resetStrategyName;


    /********实体类*****/
    /**
    * 模块
    */
    private String moduleName;


    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
