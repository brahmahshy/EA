package com.acg.easy.user.service;

import com.acg.easy.user.entity.UserDo;
import com.acg.easy.user.entity.input.UserBo;
import com.acg.easy.user.entity.output.UserVo;

/**
 * 用户服务Service层
 */
public interface UserEditService {
    /**
     * 创建用户
     *
     * @return 创建成功的用户基本信息
     */
    UserDo create(UserBo params);

    /**
     * 更新用户
     *
     * @param bo 需要更新的用户id
     * @return 更新后的基本用户信息
     */
    UserVo update(UserBo bo);

    /**
     * 删除用户
     *
     * @param userId 需要删除的用户id
     */
    void delete(Long userId);
}
