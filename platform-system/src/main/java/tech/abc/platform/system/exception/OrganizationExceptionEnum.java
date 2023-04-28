package tech.abc.platform.system.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;


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
    NOT_FOUND("未找到组织机构【{0}】"),
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
