package com.easyacg.controller.user;

import com.easyacg.core.entity.ResponseVo;
import com.easyacg.user.entity.output.UserVo;
import com.easyacg.user.service.UserQueryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserQueryController {
    @Resource
    private UserQueryService userService;

    @PostMapping("/getByName")
    public ResponseVo<UserVo> getByName(String name) {
        return ResponseVo.success(userService.getByName(name));
    }
}
