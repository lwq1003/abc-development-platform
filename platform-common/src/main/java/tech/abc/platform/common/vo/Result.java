package tech.abc.platform.common.vo;

import lombok.Data;

/**
 * 返回给前端的处理结果
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class Result {


    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

}
