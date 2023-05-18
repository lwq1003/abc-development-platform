package tech.abc.platform.system.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 组织机构导入 视图对象类
 *
 * @author wqliu
 * @date 2023-05-18
 */
@Data
public class OrganizationForImportVO {


    /**
     * 名称
     */
    @NotBlank(message = "【名称】不能为空")
    @ExcelProperty("名称")
    private String name;

    /**
     * 编码
     */
    @ExcelProperty("编码")
    private String code;


    /**
     * 类型
     */
    @NotBlank(message = "【类型】不能为空")
    @ExcelProperty("类型")
    private String typeName;

    /**
     * 上级名称
     */
    @ExcelProperty("上级名称")
    private String parentName;

    /**
     * 上级编码
     */
    @ExcelProperty("上级编码")
    private String parentCode;


    /**
     * 排序
     */
    @ExcelProperty("排序")
    private String orderNo;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;


}
