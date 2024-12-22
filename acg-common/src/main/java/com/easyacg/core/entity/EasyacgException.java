package com.easyacg.core.entity;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * 通用异常信息
 *
 * @author brahma
 */
@Slf4j
public class EasyacgException extends RuntimeException {
    private EasyacgException(String message) {
        super(message);
    }

    private EasyacgException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EasyacgException build(String message) {
        return new EasyacgException(message);
    }

    public static EasyacgException build(String message, Object... args) {
        return new EasyacgException(MessageFormat.format(message, args));
    }

    public static EasyacgException build(String message, Throwable cause) {
        return new EasyacgException(message, cause);
    }

    public static EasyacgException build(Throwable cause, String message, Object... args) {
        return new EasyacgException(MessageFormat.format(message, args), cause);
    }
}
