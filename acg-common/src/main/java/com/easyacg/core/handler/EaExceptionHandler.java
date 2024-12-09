package com.easyacg.core.handler;

import com.easyacg.core.entity.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常处理类
 *
 * @author brahma
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EaExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseVo<T> handleValidationException(MethodArgumentNotValidException exception) {
        log.error("参数校验失败：", exception);
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(result -> result.getField() + result.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseVo.error("1.1.1", "参数校验失败", String.join(", ", errors));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public <T> ResponseVo<T> handlerDuplicateKeyException(DuplicateKeyException exception) {
        log.error("操作失败：", exception);
        return ResponseVo.error("1.1.1", "SQL执行失败", exception.getCause().getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public <T> ResponseVo<T> handlerSqlException(SQLException exception) {
        log.error("操作失败：", exception);
        return ResponseVo.error("1.1.1", "SQL执行失败", exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseVo<T> handlerException(Exception exception) {
        log.error("操作失败：", exception);
        return ResponseVo.error("1.1.0", "操作失败", exception.getMessage());
    }
}
