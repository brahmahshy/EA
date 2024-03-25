package com.brahma.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String name;

    private String telephone;

    private String email;
}
