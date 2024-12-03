package com.easyacg.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * controller接口，统一返参
 *
 * @author brahma
 */
@Getter
@Setter
public class ResponseVo<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseError error;

    private ResponseStatus status;

    private ResponseVo(T data, ResponseStatus status) {
        this.data = data;
        this.status = status;
    }

    public ResponseVo(ResponseError error, ResponseStatus status) {
        this.error = error;
        this.status = status;
    }

    public static ResponseVo<Void> success() {
        return ResponseVo.success(null);
    }

    public static <T> ResponseVo<T> success(T vo) {
        return new ResponseVo<>(vo, ResponseStatus.SUCCESS);
    }

    public static <T> ResponseVo<T> error(String code, String message, String detailMessage) {
        return new ResponseVo<>(new ResponseError(code, message, detailMessage), ResponseStatus.ERROR);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseError {
        /**
         * 异常编码
         */
        private String code;

        /**
         * 异常信息
         */
        private String message;

        /**
         * 异常详细信息
         */
        private String detailMessage;
    }

    @Getter
    public enum ResponseStatus {
        SUCCESS,
        ERROR
    }
}
