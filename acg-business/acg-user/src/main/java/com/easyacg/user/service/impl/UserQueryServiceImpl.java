package com.easyacg.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyacg.user.entity.output.UserVo;
import com.easyacg.user.mapper.UserMapper;
import com.easyacg.user.model.User;
import com.easyacg.user.service.UserQueryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserVo getByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        User userDo = userMapper.selectOne(wrapper);
        return UserVo.buildByDo(userDo);
    }
}
