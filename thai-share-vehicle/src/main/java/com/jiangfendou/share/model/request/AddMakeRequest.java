package com.jiangfendou.share.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * AddMakeRequest.
 * @author jiangmh
 */
@Data
public class AddMakeRequest {

    @NotBlank
    private String makeCode;

    @NotBlank
    private String makeName;

    /**
     * 0: inactive, 1: active
     */
    @NotNull
    private Integer isActive;
}
