package com.brahma.controller;

import jakarta.annotation.Resource;

import com.brahma.entity.ResponseVo;
import com.brahma.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserQueryController {
    @Resource
    private UserService userService;

    @PostMapping("/getByName")
    public ResponseVo<String> getByName() {
        return ResponseVo.success("user查詢成功");
    }
}
