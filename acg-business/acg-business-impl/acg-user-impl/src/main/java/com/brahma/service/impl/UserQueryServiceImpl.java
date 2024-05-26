package com.brahma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;

import com.brahma.entity.UserDo;
import com.brahma.entity.response.UserVo;
import com.brahma.mapper.UserMapper;
import com.brahma.service.UserQueryService;

import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserVo getByName(String name) {
        QueryWrapper<UserDo> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        UserDo userDo = userMapper.selectOne(wrapper);
        return UserVo.buildByDo(userDo);
    }
}
