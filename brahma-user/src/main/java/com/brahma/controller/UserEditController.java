package com.brahma.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import com.brahma.entity.BaseIdBo;
import com.brahma.entity.RequestBo;
import com.brahma.entity.ResponseVo;
import com.brahma.entity.UserDo;
import com.brahma.entity.request.UserBo;
import com.brahma.entity.response.UserVo;
import com.brahma.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEditController {
    @Resource
    private UserService userService;

    /**
     * 创建用户
     *
     * @return 用户基本信息
     */
    @PostMapping("/create")
    public ResponseVo<UserDo> create(@RequestBody @Valid RequestBo<UserBo> bo) {
        return ResponseVo.success(userService.create(bo.getParams()));
    }

    /**
     * 根据userId更新用户
     *
     * @param bo 接口入参
     * @return 更新后的用户基本信息
     */
    @PostMapping("/update")
    @Operation(description = "根据userId更新用户")
    public ResponseVo<UserVo> update(@RequestBody @Valid RequestBo<UserBo> bo) {
        return ResponseVo.success(userService.update(bo.getParams()));
    }

    /**
     * 根据userId删除用户
     *
     * @param bo 接口入参
     * @return 是否删除成功
     */
    @DeleteMapping("/delete")
    @Operation(description = "根据userId删除用户")
    public ResponseVo<Void> delete(@RequestBody @Valid RequestBo<BaseIdBo> bo) {
        userService.delete(bo.getParams().getId());
        return ResponseVo.success(null);
    }
}
