package tech.abc.edoc.edoc.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 文件夹 视图对象类
*
* @author wqliu
* @date 2024-02-01
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FolderVO extends BaseVO {
    /**
    * 上级
    */
    private String parentId;

    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;




    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/
    /**
    * 上级
    */
    private String parentIdName;


    /********范围查询*****/

    /********自定义扩展*****/
    /**
    * 忽略上级
    */
    private Boolean ignoreParent;


    /********子对象*****/




}
