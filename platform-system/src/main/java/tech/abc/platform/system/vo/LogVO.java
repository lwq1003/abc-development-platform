package tech.abc.platform.system.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 日志 视图对象类
*
* @author wqliu
* @date 2023-04-26
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogVO extends BaseVO {
    /**
    * 内容
    */
    private String content;

    /**
    * 日志类型
    */
    private String logType;

    /**
    * 请求时间
    */
    private LocalDateTime requestTime;

    /**
    * 请求参数
    */
    private String requestParam;

    /**
    * 请求路径
    */
    private String requestPath;

    /**
    * 请求方法
    */
    private String requestMethod;

    /**
    * 操作人标识
    */
    private String operatorId;

    /**
    * 操作人账号
    */
    private String operatorAccount;

    /**
    * 操作人姓名
    */
    private String operatorName;

    /**
    * 操作人ip
    */
    private String operatorIp;

    /**
    * 响应结果
    */
    private String responseCode;

    /**
    * 响应数据
    */
    private String responseData;

    /**
    * 执行耗时ms
    */
    private Integer timeConsuming;


    /********字典类*****/
    /**
    * 日志类型
    */
    private String logTypeName;

    /**
     * 响应结果
     */
    private String responseCodeName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
