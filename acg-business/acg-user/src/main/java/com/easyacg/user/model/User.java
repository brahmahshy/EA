package com.easyacg.user.model;

import com.easyacg.core.mybatis.TableBaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.mpe.annotation.DefaultValue;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.Table;

/**
 * 用户
 *
 * @author brahma
 */
@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class User extends TableBaseDo {
    @Column(comment = "用户名称", notNull = true)
    private String name;

    @DefaultValue("123456")
    @ColumnComment("用户头像地址")
    private String imageUrl;

    @ColumnComment("用户电话号码")
    private String telephone;

    @ColumnComment("用户邮箱地址")
    private String email;
}
