package tech.abc.platform.support.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

/**
 * 附件 视图对象类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AttachmentVO extends BaseVO {
    /**
     * 名称
     */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
     * 大小
     */
    private String size;

    /**
     * 长度
     */
    private Long length;

    /**
     * 路径
     */
    private String path;

    /**
     * 类型
     */
    private String type;

    /**
     * 实际名称
     */
    private String realName;

    /**
     * 实体
     */
    private String entity;

    /**
     * 备注
     */
    private String ramark;


    /********非库表存储属性*****/


    /********字典类*****/

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/


}
