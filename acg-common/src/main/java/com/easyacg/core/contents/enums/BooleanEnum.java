package com.easyacg.core.contents.enums;

/**
 * 布尔枚举
 *
 * @author brahma
 */
public enum BooleanEnum {
    TRUE,
    Y,
    FALSE,
    N;

    /**
     * 判断是否为true
     *
     * @param value 值
     * @return 是否为true
     */
    public static boolean isTrue(String value) {
        return TRUE.name().equalsIgnoreCase(value) || Y.name().equalsIgnoreCase(value);
    }

    /**
     * 判断是否为false
     *
     * @param value 值
     * @return 是否为false
     */
    public static boolean isFalse(String value) {
        return FALSE.name().equalsIgnoreCase(value) || N.name().equalsIgnoreCase(value);
    }
}
