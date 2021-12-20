package com.jiangfendou.share.controller;


import com.jiangfendou.share.common.ApiResponse;
import com.jiangfendou.share.model.request.AddMakeRequest;
import com.jiangfendou.share.model.request.SearchMakeRequest;
import com.jiangfendou.share.service.MakeService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/make")
public class MakeController {

    @Autowired
    private MakeService makeService;

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('p3')")
    public ApiResponse searchMake(SearchMakeRequest searchMakeRequest) {

        return ApiResponse.success(makeService.searchMake(searchMakeRequest));
    }

    @PostMapping("/add")
    public ApiResponse addMake(@RequestBody @Valid AddMakeRequest addMakeRequest) {
        makeService.addMake(addMakeRequest);
        return ApiResponse.success();
    }
}

