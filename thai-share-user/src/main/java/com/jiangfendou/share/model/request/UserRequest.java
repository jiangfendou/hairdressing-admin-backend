package com.jiangfendou.share.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * UserRequest.
 * @author jiangmh
 */
@Data
public class UserRequest {

    private static final long serialVersionUID=1L;

    private String id;

//    @NotBlank
    private String userName;

//    @NotBlank
    private String userPassword;

//    @NotBlank
    private String fullName;

//    @NotBlank
    private String mobile;

    private String creator;

    private String updater;

}
