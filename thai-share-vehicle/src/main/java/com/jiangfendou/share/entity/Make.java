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
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Make implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private String makeId;

    private String makeCode;

    private String makeName;

    /**
     * 0: inactive, 1: active
     */
    private Integer isActive;

    /**
     * 0: enabled; 1: disabled
     */
    private Integer isDeleted;

    private Integer lockVersion;

    private String creator;

    private LocalDateTime createDatetime;

    private String updater;

    private LocalDateTime updateDatetime;

    private LocalDateTime deleteDatetime;


}
