package tech.abc.platform.cip.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * API服务日志 视图对象类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ApiServiceLogVO extends BaseVO {
    /**
     * 应用编码
     */
    @NotBlank(message = "【应用编码】不能为空")
    private String appCode;

    /**
     * 服务编码
     */
    @NotBlank(message = "【服务编码】不能为空")
    private String serviceCode;

    /**
     * 请求时间
     */
    private LocalDateTime requestTime;

    /**
     * 收到时间
     */
    private LocalDateTime receiveTime;

    /**
     * 请求业务数据
     */
    private String requestBusinessData;

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
     * 响应时间
     */
    private LocalDateTime responseTime;

    /**
     * 响应业务数据
     */
    private String responseBusinessData;

    /**
     * 执行耗时ms
     */
    private Long executionTime;


    /********字典类*****/

    /********实体类*****/

    /********范围查询*****/
    /**
     * 请求时间起
     */
    private String requestTimeBeginForQuery;

    /**
     * 请求时间止
     */
    private String requestTimeEndForQuery;
    /**
     * 执行耗时ms起
     */
    private String executionTimeBeginForQuery;

    /**
     * 执行耗时ms止
     */
    private String executionTimeEndForQuery;

    /********自定义扩展*****/

    /********子对象*****/


}
