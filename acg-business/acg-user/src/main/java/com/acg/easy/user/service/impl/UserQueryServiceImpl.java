package com.acg.easy.user.service.impl;

import com.acg.easy.user.entity.UserDo;
import com.acg.easy.user.entity.output.UserVo;
import com.acg.easy.user.mapper.UserMapper;
import com.acg.easy.user.service.UserQueryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
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
