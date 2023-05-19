package tech.abc.platform.system.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


/**
 * 用户导入 视图对象类
 *
 * @author wqliu
 * @date 2023-05-19
 */
@Data
public class UserForImportVO {

    /**
     * 姓名
     */
    @NotBlank(message = "【姓名】不能为空")
    @ExcelProperty("姓名")
    private String name;

    /**
     * 账号
     */
    @NotBlank(message = "【账号】不能为空")
    @ExcelProperty("账号")
    private String account;


    /**
     * 性别
     */
    @NotBlank(message = "【性别】不能为空")
    @ExcelProperty("性别")
    private String genderName;

    /**
     * 组织机构名称
     */
    @ExcelProperty("组织机构名称")
    private String organizationName;

    /**
     * 组织机构编码
     */
    @ExcelProperty("组织机构编码")
    private String organizationCode;

    /**
     * 出生日期
     */
    @ExcelProperty("出生日期")
    private LocalDateTime birthday;

    /**
     * 手机号
     */
    @ExcelProperty("手机号")
    private String telephone;

    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    private String email;

    /**
     * 职位
     */
    @ExcelProperty("职位")
    private String position;


    /**
     * 排序
     */
    @ExcelProperty("排序")
    private String orderNo;


}


