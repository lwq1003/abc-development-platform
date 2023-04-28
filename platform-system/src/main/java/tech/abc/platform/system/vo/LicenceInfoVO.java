package tech.abc.platform.system.vo;

import lombok.Data;

/**
 * 授权信息
 *
 * @author wqliu
 * @date 2023-03-08
 **/
@Data
public class LicenceInfoVO {

    /**
     * 申请码
     */
    private String applyCode;

    /**
     * 授权码
     */
    private String licenceCode;

    /**
     * 授权是否有效
     */
    private String validFlag;

    /**
     * 授权无效原因
     */
    private String invalidReason;
}
