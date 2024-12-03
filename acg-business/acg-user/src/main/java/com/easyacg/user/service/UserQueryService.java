package com.easyacg.user.service;

import com.easyacg.user.entity.output.UserVo;

public interface UserQueryService {
    UserVo getByName(String name);
}
