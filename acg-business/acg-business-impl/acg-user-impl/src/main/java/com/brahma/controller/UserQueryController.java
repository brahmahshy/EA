package com.brahma.controller;

import jakarta.annotation.Resource;

import com.brahma.entity.ResponseVo;
import com.brahma.entity.response.UserVo;
import com.brahma.service.UserQueryService;

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
