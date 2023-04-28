package tech.abc.platform.common.utils;


import com.alibaba.fastjson.JSON;
import tech.abc.platform.common.vo.Result;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * 返回给前端结果的工具类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@UtilityClass
@Slf4j
public class ResultUtil {


    /**
     * 返回成功
     *
     * @param data 数据
     * @return
     */
    public static ResponseEntity<Result> success(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setMessage("操作成功");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public static ResponseEntity<Result> success(Object data, String message) {
        Result result = new Result();
        result.setData(data);
        result.setMessage(message);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 提供给部分不需要出參的接口
     */
    public static ResponseEntity<Result> success() {
        return success(null);
    }


    /**
     * 返回异常信息，在已知的范围内
     */
    public static ResponseEntity<Result> error(String message, HttpStatus httpStatus) {
        Result result = new Result();
        result.setMessage(message);
        result.setData(null);
        return new ResponseEntity<>(result, httpStatus);

    }


    /**
     * 返回json数据至前端
     * 部分调用是从第三方框架如Spring Security发起，不经过controller层，使用该方法将处理结果直接返回给前端
     *
     * @param response 响应
     * @param result   结果
     */
    public static void returnJsonToFront(HttpServletResponse response, ResponseEntity<Result> result) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(result.getStatusCodeValue());
        try {
            response.getWriter().write(JSON.toJSONString(result.getBody()));
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("返回json数据出错", e);
        }

    }
}
