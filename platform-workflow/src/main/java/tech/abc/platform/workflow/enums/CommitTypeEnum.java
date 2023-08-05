package tech.abc.platform.workflow.enums;

import lombok.Getter;

/**
 * 用户状态
 * @author wqliu
 */
@Getter
public enum CommitTypeEnum {

    /**
     * 提交
     */
    COMMIT,
    /**
     * 转办
     */
    TRANSFER,
    /**
     * 委派
     */
    DELEGATE,
    /**
     * 驳回
     */
    REJECT,
    /**
     * 签收
     */
    SIGN_IN,
    /**
     * 撤销签收
     */
    CANCEL_SIGN_IN,
    ;

}
