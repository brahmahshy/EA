package com.easyacg.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easyacg.core.mybatis.TableBaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户Do
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user", schema = "brahma")
public class UserDo extends TableBaseDo {
    private String name;

    private String imageUrl;

    private String telephone;

    private String email;
}
