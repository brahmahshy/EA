package com.acg.easy.user.service;

import com.acg.easy.user.entity.output.UserVo;

public interface UserQueryService {
    UserVo getByName(String name);
}
