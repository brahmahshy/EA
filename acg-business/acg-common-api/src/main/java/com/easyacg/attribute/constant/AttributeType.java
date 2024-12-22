package com.easyacg.attribute.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeType {
    STRING("string"),

    LONG("long"),

    DATE("date");

    @EnumValue
    private final String value;
}
