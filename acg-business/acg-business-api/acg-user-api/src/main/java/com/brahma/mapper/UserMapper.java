package com.brahma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.brahma.entity.UserDo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDo> {

}
