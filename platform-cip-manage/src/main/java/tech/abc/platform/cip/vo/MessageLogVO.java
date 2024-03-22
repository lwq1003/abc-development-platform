package tech.abc.platform.cip.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import java.time.LocalDateTime;

/**
 * 消息日志 视图对象类
 *
 * @author wqliu
 * @date 2024-03-20
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


    /********非库表存储属性*****/


    /********字典类*****/
    /**
     * 响应结果
     */
    private String responseResultName;

    /**
     * 状态
     */
    private String statusName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/
    /**
     * 响应时间起
     */
    private String responseTimeBeginForQuery;

    /**
     * 响应时间止
     */
    private String responseTimeEndForQuery;

    /**
     * 请求时间起
     */
    private String requestTimeBeginForQuery;

    /**
     * 请求时间止
     */
    private String requestTimeEndForQuery;


    /********自定义扩展*****/

    /********子对象*****/


}
