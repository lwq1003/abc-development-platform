package tech.abc.platform.common.exception;

import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * 全局异常处理类
 *
 * @author wqliu
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandle {


    // 常见Http异常

    /**
     * 参数异常 400
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = "";
        // bean validator验证部分
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            FieldError fieldError = (FieldError) allErrors.get(0);
            errorMessage = fieldError.getDefaultMessage();
        }
        return logAndGenerateResult(e, HttpStatus.BAD_REQUEST, errorMessage);
    }


    /**
     * 未授权，401
     */
    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<Result> handleSessionExpiredException(SessionExpiredException e) {

        // 会话超时为正常现象，虽然使用异常来处理，但不调用log.error
        return ResultUtil.error(e.getMessage(), HttpStatus.UNAUTHORIZED);

    }

    /**
     * 无权限 403
     * SpringSecurity抛出的权限验证失败并不会被本类全局异常处理捕获
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result> handleDuplicateKeyException(AccessDeniedException e) {
        return logAndGenerateResult(e, HttpStatus.FORBIDDEN);

    }

    /**
     * 未找到异常 404
     * 注意：404错误会被外部容器（tomcat）拦截并处理，到不了controller层，也进不了该处理
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Result> handleNoFoundException(NoHandlerFoundException e) {

        return logAndGenerateResult(e, HttpStatus.NOT_FOUND);

    }


    /**
     * 未找到异常 405
     * 注意：405错误会被外部容器（tomcat）拦截并处理，到不了controller层，也进不了该处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        return logAndGenerateResult(e, HttpStatus.METHOD_NOT_ALLOWED);

    }


    // 数据库相关异常

    /**
     * 数据库主键重复
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Result> handleDuplicateKeyException(DuplicateKeyException e) {
        return logAndGenerateResult(e, HttpStatus.INTERNAL_SERVER_ERROR, "数据库主键键值重复");
    }

    /**
     * 字段截断异常，也即字段过长
     * 实际会被下面的DataIntegrityViolationException包装后抛出，因此实际未起作用
     */
    @ExceptionHandler(MysqlDataTruncation.class)
    public ResponseEntity<Result> handleMysqlDataTruncation(MysqlDataTruncation e) {
        return logAndGenerateResult(e, HttpStatus.INTERNAL_SERVER_ERROR, "字段过长：" + e.getMessage());
    }

    /**
     * 数据写入异常
     * 通常是insert或update时违反完整性约束，例如，非空字段插入null值
     * 此外，字段过长，也会被该异常捕获并优先触发
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Result> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return logAndGenerateResult(e, HttpStatus.INTERNAL_SERVER_ERROR, "数据保存异常：" + e.getMessage());
    }


    // 自定义异常

    /**
     * 自定义异常 500
     */
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Result> handleCustomException(CustomException e) {
        return logAndGenerateResult(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 其他异常

    /**
     * 其他异常 500
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> handleException(Exception e) {
        return logAndGenerateResult(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // 异常辅助

    /**
     * 日志记录及生成返回结果
     */
    private ResponseEntity<Result> logAndGenerateResult(Exception e, HttpStatus httpStatus) {

        log.error(e.getMessage(), e);
        return ResultUtil.error(e.getMessage(), httpStatus);
    }

    /**
     * 日志记录及生成返回结果，使用自定义消息
     */
    private ResponseEntity<Result> logAndGenerateResult(Exception e, HttpStatus httpStatus, String message) {
        log.error(e.getMessage(), e);
        return ResultUtil.error(message, httpStatus);
    }

}
