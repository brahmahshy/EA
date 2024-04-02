package com.brahma.service.impl;

import jakarta.annotation.Resource;

import com.brahma.entity.UserDo;
import com.brahma.entity.request.UserBo;
import com.brahma.entity.response.UserVo;
import com.brahma.mapper.UserMapper;
import com.brahma.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDo create(UserBo params) {
        UserDo userDo = new UserDo();
        userDo.setId(Integer.valueOf(UUID.randomUUID().toString()));
        userDo.setName(params.getName());
        userDo.setImageUrl(params.getImageUrl());
        userDo.setTelephone(params.getTelephone());
        userDo.setEmail(params.getEmail());
        userDo.setCreatorId(1);
        userDo.setUpdaterId(1);
        userMapper.insert(userDo);
        return userDo;
    }

    @Override
    @Transactional
    public UserVo update(UserBo bo) {
        UserDo userDo = new UserDo();
        userDo.setId(bo.getId());
        userDo.setName(bo.getName());
        userDo.setTelephone(bo.getTelephone());
        userDo.setEmail(bo.getEmail());
        userMapper.updateById(userDo);
        return new UserVo();
    }

    @Override
    public void delete(Integer userId) {
        UserDo userDo = new UserDo();
        userDo.setId(userId);
        userMapper.deleteById(userDo);
    }
}
