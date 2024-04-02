package com.brahma.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户Do
 */
@Getter
@Setter
@TableName(value = "user", schema = "brahma")
public class UserDo extends TableBaseDo {
    private String name;

    private String imageUrl;

    private String telephone;

    private String email;
}
