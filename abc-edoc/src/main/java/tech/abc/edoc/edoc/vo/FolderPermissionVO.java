package tech.abc.edoc.edoc.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 文件夹权限 视图对象类
*
* @author wqliu
* @date 2024-02-04
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FolderPermissionVO extends BaseVO {
    /**
    * 文件夹标识
    */
    @NotBlank(message = "【文件夹标识】不能为空")
    private String folderId;

    /**
    * 授权对象类型
    */
    @NotBlank(message = "【授权对象类型】不能为空")
    private String objectType;

    /**
    * 授权对象标识
    */
    @NotBlank(message = "【授权对象标识】不能为空")
    private String objectId;

    /**
    * 权限编码
    */
    private String permissionCode;


    /********非库表存储属性*****/
    private List<String> permissionCodeList;


    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
