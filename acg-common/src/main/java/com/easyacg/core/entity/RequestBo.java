package com.easyacg.core.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBo<T> {
    private T params;
}
