package tech.abc.platform.cip.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 消息日志 视图对象类
*
* @author wqliu
* @date 2023-05-03
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MessageLogVO extends BaseVO {
    /**
    * 请求标识
    */
    private String requestId;

    /**
    * 请求应用编码
    */
    private String requestAppCode;

    /**
    * 请求主题编码
    */
    private String requestTopicCode;

    /**
    * 请求时间
    */
    private LocalDateTime requestTime;

    /**
    * 请求数据
    */
    private String requestData;

    /**
    * 响应标识
    */
    private String responseId;

    /**
    * 响应应用编码
    */
    private String responseAppCode;

    /**
    * 响应主题编码
    */
    private String responseTopicCode;

    /**
    * 响应时间
    */
    private LocalDateTime responseTime;

    /**
    * 响应数据
    */
    private String responseData;

    /**
    * 响应结果
    */
    private String responseResult;

    /**
    * 错误编码
    */
    private String errorCode;

    /**
    * 错误信息
    */
    private String errorMessage;

    /**
    * 状态
    */
    private String status;

    /**
    * 发送次数
    */
    private Integer sendCount;


    /********字典类*****/
    /**
    * 状态
    */
    private String statusName;

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
    * 响应时间起
    */
    private String responseTimeBeginForQuery;

    /**
    * 响应时间止
    */
    private String responseTimeEndForQuery;

    /********自定义扩展*****/

    /********子对象*****/




}
