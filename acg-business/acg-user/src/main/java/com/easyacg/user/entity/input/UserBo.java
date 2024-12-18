package com.easyacg.user.entity.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Valid
@Getter
@Setter
@Schema(description = "用户商业业务对象")
public class UserBo {
    @NotEmpty
    @Schema(description = "用户名称")
    private String name;

    @NotEmpty
    @Schema(description = "用户头像url")
    private String imageUrl;

    @NotEmpty
    @Schema(description = "用户电话")
    private String telephone;

    @Email
    @NotEmpty
    @Schema(description = "用户邮箱")
    private String email;
}
