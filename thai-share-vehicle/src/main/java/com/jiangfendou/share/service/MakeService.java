package com.jiangfendou.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiangfendou.share.entity.Make;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangfendou.share.model.request.AddMakeRequest;
import com.jiangfendou.share.model.request.SearchMakeRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-07
 */
public interface MakeService extends IService<Make> {

    /**
     * search make
     * */
    IPage<Make> searchMake(SearchMakeRequest searchMakeRequest);

    /**
     * add make
     * */
   void addMake(AddMakeRequest addMakeRequest);
}
