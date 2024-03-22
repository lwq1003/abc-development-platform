package tech.abc.platform.cip.common.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * api请求
 *
 * @author wqliu
 * @date 2021-8-19
 **/
@Data
public class ApiRequest {

    /**
     * 应用代码
     */
    @NotBlank(message = "应用代码不能为空")
    private String appCode;

    /**
     * 服务代码
     */
    @NotBlank(message = "服务代码不能为空")
    private String serviceCode;

    /**
     * 请求时间
     */
    @NotBlank(message = "请求时间不能为空")
    private String requestTime;

    /**
     * 签名
     */
    @NotBlank(message = "签名不能为空")
    private String sign;

    /**
     * 业务数据
     */
    private String data;


    /**
     * 签名方法（尚未启用，目前默认使用MD5）
     */
    private String signMethod;
}
