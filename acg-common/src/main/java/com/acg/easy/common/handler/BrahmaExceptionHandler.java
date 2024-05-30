package com.acg.easy.common.handler;

import com.acg.easy.common.entity.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class BrahmaExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public <T> ResponseVo<T> handlerSqlException(SQLException sqlException) {
        return ResponseVo.error("1.1.1", "SQL执行失败", sqlException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseVo<T> handlerException(Exception exception) {
        return ResponseVo.error("1.1.0", "操作失败", exception.getMessage());
    }
}
