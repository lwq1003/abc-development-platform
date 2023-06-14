package tech.abc.platform.support.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 组件 视图对象类
 *
 * @author wqliu
 * @date 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PortletVO extends BaseVO {
    /**
     * 分类
     */
    @NotBlank(message = "【分类】不能为空")
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
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 状态
     */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
     * 排序
     */
    private String orderNo;

    /**
     * 备注
     */
    private String remark;


    /********非库表存储属性*****/
    private List<PortletParamVO> paramList;


    /********字典类*****/
    /**
     * 分类
     */
    private String categoryName;

    /**
     * 状态
     */
    private String statusName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/


}
