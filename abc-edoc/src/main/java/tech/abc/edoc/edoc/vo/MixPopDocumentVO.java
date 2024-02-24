package tech.abc.edoc.edoc.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 文件夹和文档混合 视图对象
 *
 * @author wqliu
 * @date 2021-03-08
 */
@Data
public class MixPopDocumentVO  {


    /**
     * 目标文件夹标识
     */
    private String targetFolderId;


    /**
     * 数据
     */
    private List<PopDocumentVO> data;


    /**
     * 是否保留权限
     */
    private String retainPermission;



}
