package com.jiangfendou.share.controller;


import com.jiangfendou.share.common.ApiResponse;
import com.jiangfendou.share.entity.User;
import com.jiangfendou.share.model.request.UserRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-12
 */
@Controller
@RequestMapping("/users")
public class UserController {


    @PostMapping("/login")
    public ApiResponse addUser(@RequestBody UserRequest userRequest) {

        System.out.println("++++++++++++++++++++++++++");
        return ApiResponse.success();
    }

    @PostMapping("/test")
    public ApiResponse test(@RequestBody UserRequest userRequest) {

        System.out.println("===========================");
        return ApiResponse.success();
    }
}

