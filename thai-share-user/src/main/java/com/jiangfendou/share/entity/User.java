package com.jiangfendou.share.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiangfendou
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String userName;

    private String userPassword;

    private String fullName;

    private String mobile;

    private Integer lockVersion;

    /**
     * 0: enabled; 1: disabled
     */
    private Integer isDeleted;

    private String creator;

    private LocalDateTime createDatetime;

    private LocalDateTime deleteDatetime;

    private String updater;

    private LocalDateTime updateDatetime;


}
