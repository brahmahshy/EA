package com.easyacg.core.handler;

import com.easyacg.core.entity.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

/**
 * 异常处理类
 *
 * @author brahma
 */
@Slf4j
@RestControllerAdvice
public class BrahmaExceptionHandler extends ResponseEntityExceptionHandler {
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
