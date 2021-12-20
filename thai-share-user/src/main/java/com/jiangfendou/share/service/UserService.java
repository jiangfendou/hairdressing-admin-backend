package com.jiangfendou.share.service;

import com.jiangfendou.share.common.ApiResponse;
import com.jiangfendou.share.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-12
 */
public interface UserService extends IService<User> {

    /**
     * 添加用户
     * */
    void addUser(User user);


}
