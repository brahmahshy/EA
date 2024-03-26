package com.brahma.service.impl;

import jakarta.annotation.Resource;

import com.brahma.entity.UserDo;
import com.brahma.entity.request.UserBo;
import com.brahma.entity.response.UserVo;
import com.brahma.mapper.UserMapper;
import com.brahma.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public String create() {
        UserDo userDo = new UserDo();
        userDo.setName("admin");
        userDo.setTelephone("15270276124");
        userDo.setEmail("875101222@qq.com");
        userDo.setCreatorId(1);
        userDo.setUpdaterId(1);
        userMapper.insert(userDo);
        return "创建成功";
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
