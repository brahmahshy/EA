package com.easyacg.core.handler;

import com.easyacg.core.entity.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> errors = new ArrayList<>();
        List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();
        for (ObjectError objectError : objectErrors) {
            if (objectError instanceof FieldError fieldError) {
                errors.add(fieldError.getField() + fieldError.getDefaultMessage());
            } else {
                errors.add(objectError.getDefaultMessage());
            }
        }
        return ResponseVo.error("1.2.0", "参数校验失败", String.join(", ", errors));
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
