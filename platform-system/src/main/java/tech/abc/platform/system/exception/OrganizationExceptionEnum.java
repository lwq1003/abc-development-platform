package tech.abc.platform.system.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 组织机构相关异常
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum OrganizationExceptionEnum implements ExceptionInterface {

    /**
     * 未找到组织机构
     */
    NAME_NOT_FOUND("未找到上级名称"),
    /**
     * 未找到组织机构编码
     */
    CODE_NOT_FOUND("未找到上级编码"),

    /**
     * 编码和名称不能同时为空
     */
    PARENT_NAME_AND_CODE_CANOT_NULL("上级名称和上级编码不能同时为空"),
    /**
     * 组织机构下存在人员，不能删除
     */
    HAS_USER("组织机构下存在人员，不能删除"),
    ;


    private String message;

    OrganizationExceptionEnum(String message) {
        this.message = message;
    }

}
