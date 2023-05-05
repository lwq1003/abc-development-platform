package tech.abc.platform.cip.common.entity;

import lombok.Data;

/**
 * api 响应
 *
 * @author wqliu
 * @date 2021-8-19
 **/
@Data
public class ApiResponse {

    /**
     * 执行结果
     */
    private String executeResult;

    /**
     * 错误编码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 业务数据
     */
    private String data;
}
