package com.easyacg.user.entity.output;

import com.easyacg.core.entity.EasyacgException;
import com.easyacg.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "返回用户基本信息")
public class UserVo {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "用户手机")
    private String telephone;

    @Schema(description = "用户邮箱")
    private String email;

    public static UserVo buildByDo(User userDo) {
        if (userDo == null) {
            throw EasyacgException.build("用户不存在！！！");
        }

        UserVo userVo = new UserVo();
        userVo.setId(userDo.getId());
        userVo.setName(userDo.getName());
        userVo.setTelephone(userDo.getTelephone());
        userVo.setEmail(userDo.getEmail());
        return userVo;
    }
}
