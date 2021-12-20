package com.jiangfendou.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangfendou.share.entity.Make;
import com.jiangfendou.share.mapper.MakeMapper;
import com.jiangfendou.share.model.request.AddMakeRequest;
import com.jiangfendou.share.model.request.SearchMakeRequest;
import com.jiangfendou.share.service.MakeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-07
 */
@Service
public class MakeServiceImpl extends ServiceImpl<MakeMapper, Make> implements MakeService {

    @Autowired
    private IService<Make> service;

    @Override
    public IPage<Make> searchMake(SearchMakeRequest searchMakeRequest) {
        // 创建Page对象
        Page<Make> userPage = new Page<>(searchMakeRequest.getPageNum(), searchMakeRequest.getPageSize());
        //构建条件
        QueryWrapper<Make> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(searchMakeRequest.getMakeName()),
            "make_name", searchMakeRequest.getMakeName());
        wrapper.like(StringUtils.isNotBlank(searchMakeRequest.getMakeCode()),
            "make_code", searchMakeRequest.getMakeCode());
        wrapper.eq(searchMakeRequest.getIsActive() != null,
            "is_active", searchMakeRequest.getIsActive());
        IPage<Make> page = service.page(userPage, wrapper);
        return service.page(userPage, wrapper);
    }

    @Override
    public void addMake(AddMakeRequest addMakeRequest) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Make make = new Make();
        BeanUtils.copyProperties(addMakeRequest, make);
        make.setMakeId(com.jiangfendou.share.util.StringUtils.getUUID());
        make.setCreator(principal);
        make.setUpdater(principal);
        service.save(make);
    }
}
