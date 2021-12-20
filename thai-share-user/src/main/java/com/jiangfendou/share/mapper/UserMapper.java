package com.jiangfendou.share.mapper;

import com.jiangfendou.share.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangfendou.share.model.response.SearchUserPermissionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * searchUserPermission
     * @Param String userId
     * @Return SearchUserPermissionResponse
     **/
    SearchUserPermissionResponse searchUserPermission(@Param("userId") String userId);

}
