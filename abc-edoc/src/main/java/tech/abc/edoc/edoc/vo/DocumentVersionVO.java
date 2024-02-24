package tech.abc.edoc.edoc.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 文档版本 视图对象类
*
* @author wqliu
* @date 2024-02-04
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentVersionVO extends BaseVO {
    /**
    * 文档标识
    */
    @NotBlank(message = "【文档标识】不能为空")
    private String documentId;

    /**
    * 文档版本
    */
    @NotBlank(message = "【文档版本】不能为空")
    private String documentVersion;

    /**
    * 版本标记
    */
    private String versionTag;

    /**
    * 路径
    */
    private String path;


    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/
    /**
     * 上传人
     */
    private String uploadUserName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
