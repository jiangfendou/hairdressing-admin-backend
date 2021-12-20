package com.jiangfendou.share.model.request;

import com.jiangfendou.share.entiy.BaseEntity;
import lombok.Data;

/**
 * SearchMakeRequest.
 * @author jiangmh
 */
@Data
public class SearchMakeRequest extends BaseEntity {

    private String makeName;

    private String makeCode;

    private Boolean isActive;

}
