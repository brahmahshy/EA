package com.brahma.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "返回用户基本信息")
public class UserVo {
    @Schema(description = "用户id")
    private Integer id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "用户手机")
    private String telephone;

    @Schema(description = "用户邮箱")
    private String email;
}
