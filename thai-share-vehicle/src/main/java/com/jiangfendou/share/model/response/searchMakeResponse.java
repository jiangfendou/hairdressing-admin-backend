package com.jiangfendou.share.model.response;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * searchMakeResponse.
 * @author jiangmh
 */
@Data
public class searchMakeResponse {

    private String makeName;

    private String makeCode;

    private Integer isActive;

    private LocalDateTime updateDatetime;
}
