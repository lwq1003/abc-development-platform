package tech.abc.edoc.edoc.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 文档 视图对象类
*
* @author wqliu
* @date 2024-02-05
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentVO extends BaseVO {
    /**
    * 上级
    */
    private String parentId;

    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
    * 长度
    */
    private Long length;

    /**
    * 大小
    */
    private String size;

    /**
    * 类型
    */
    private String type;

    /**
    * 保存名称
    */
    private String realName;

    /**
    * 路径
    */
    private String path;

    /**
    * 阅读量
    */
    private Integer readCount;

    /**
    * 下载量
    */
    private Integer downloadCount;

    /**
    * 状态
    */
    private String status;

    /**
    * 上传时间
    */
    private LocalDateTime uploadTime;

    /**
    * 锁定人
    */
    private String lockId;

    /**
    * 锁定时间
    */
    private LocalDateTime lockTime;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 状态
    */
    private String statusName;


    /********实体类、用户单选、组织机构单选*****/
    /**
    * 文件夹
    */
    private String folderIdName;

    /**
    * 锁定人
    */
    private String lockIdName;


    /********范围查询*****/

    /********自定义扩展*****/
    /**
    * 忽略上级
    */
    private Boolean ignoreParent;


    /********子对象*****/




}
