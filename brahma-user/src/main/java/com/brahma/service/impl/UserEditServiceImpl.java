package com.brahma.service.impl;

import jakarta.annotation.Resource;

import com.brahma.entity.UserDo;
import com.brahma.entity.request.UserBo;
import com.brahma.entity.response.UserVo;
import com.brahma.mapper.UserMapper;
import com.brahma.service.UserEditService;
import com.brahma.util.SnowIdWorker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class UserEditServiceImpl implements UserEditService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowIdWorker snowIdWorker;

    @Override
    @Transactional
    public UserDo create(UserBo params) {
        UserDo userDo = new UserDo();
        userDo.setName(params.getName());
        userDo.setImageUrl(params.getImageUrl());
        userDo.setTelephone(params.getTelephone());
        userDo.setEmail(params.getEmail());
        userMapper.insert(userDo);
        System.out.println(userDo);
        return userDo;
    }

    @Override
    @Transactional
    public UserVo update(UserBo bo) {
        UserDo userDo = new UserDo();
        userDo.setName(bo.getName());
        userDo.setTelephone(bo.getTelephone());
        userDo.setEmail(bo.getEmail());
        userMapper.updateById(userDo);
        return new UserVo();
    }

    @Override
    public void delete(Long userId) {
        UserDo userDo = new UserDo();
        userDo.setId(userId);
        userMapper.deleteById(userDo);
    }
}
