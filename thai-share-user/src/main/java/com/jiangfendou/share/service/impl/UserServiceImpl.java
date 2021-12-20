package com.jiangfendou.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangfendou.share.common.ApiError;
import com.jiangfendou.share.common.ApiResponse;
import com.jiangfendou.share.common.BusinessException;
import com.jiangfendou.share.entity.User;
import com.jiangfendou.share.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangfendou.share.model.response.SearchUserPermissionResponse;
import com.jiangfendou.share.service.UserService;
import com.jiangfendou.share.type.ErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService, UserService {

    @Autowired
    private IService<User> userIService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        User user = userIService.getOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, new ApiError(ErrorCode.NOT_FOUND_TARGET_ERROR.getCode(),
                ErrorCode.NOT_FOUND_TARGET_ERROR.getMessage()));
        }
        UserDetails userDetails = null;
        // 获取用户权限
        SearchUserPermissionResponse searchUserPermissionResponse = userMapper.searchUserPermission(user.getId());
        if (!Objects.isNull(searchUserPermissionResponse)) {
            List<SearchUserPermissionResponse.PermissionResponse> permissions =
                searchUserPermissionResponse.getPermissions();
            // 将一个集合的某一个元素放到另一个集合中
            List<String> permissionCodes = permissions.stream()
                .map(SearchUserPermissionResponse.PermissionResponse::getCode)
                .collect(Collectors.toList());
            String[] codes = new String[permissionCodes.size()];
            String[] strCodes = permissionCodes.toArray(codes);
            userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUserName()).
                password(user.getUserPassword()).
                authorities(strCodes).build();
        } else {
            userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUserName()).
                password(user.getUserPassword()).
                authorities("noRole").build();
        }
        return userDetails;
    }

    @Override
    public void addUser(User user) {
        userIService.save(user);
    }
}
