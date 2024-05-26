package com.brahma.service;

import com.brahma.entity.response.UserVo;

public interface UserQueryService {
    UserVo getByName(String name);
}
