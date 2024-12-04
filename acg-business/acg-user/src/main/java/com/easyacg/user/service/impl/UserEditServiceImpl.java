package com.easyacg.user.service.impl;

import com.easyacg.user.entity.input.UserBo;
import com.easyacg.user.entity.output.UserVo;
import com.easyacg.user.mapper.UserMapper;
import com.easyacg.user.model.User;
import com.easyacg.user.service.UserEditService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class UserEditServiceImpl implements UserEditService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public User create(UserBo params) {
        User userDo = new User();
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
        User userDo = new User();
        userDo.setName(bo.getName());
        userDo.setTelephone(bo.getTelephone());
        userDo.setEmail(bo.getEmail());
        userMapper.updateById(userDo);
        return new UserVo();
    }

    @Override
    public void delete(Long userId) {
        User userDo = new User();
        userDo.setId(userId);
        userMapper.deleteById(userDo);
    }
}
