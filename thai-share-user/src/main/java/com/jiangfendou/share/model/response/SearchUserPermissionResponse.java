package com.jiangfendou.share.model.response;

import java.util.List;
import lombok.Data;

/**
 * SearchUserPermissionResponse.
 * @author jiangmh
 */
@Data
public class SearchUserPermissionResponse {

    private String roleName;

    private List<PermissionResponse> permissions;

    @Data
    public static class PermissionResponse {

        private String code;
    }
}
