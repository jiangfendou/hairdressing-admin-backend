package com.jiangfendou.share.filter;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.jiangfendou.share.common.ApiError;
import com.jiangfendou.share.common.BusinessException;
import com.jiangfendou.share.type.ErrorCode;
import com.jiangfendou.share.util.EncryptUtil;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GatewayFilter.
 * @author jiangmh
 */
@Slf4j
@Component
public class GatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private TokenStore tokenStore;

    private static final String UAA_USER = "/user/**";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // uaa服务所有放行
        if (pathMatcher.match(UAA_USER, requestUrl)) {
            return chain.filter(exchange);
        }

        // 检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)) {
            log.info("token不能为空");
            throw new BusinessException(HttpStatus.NOT_FOUND, new ApiError(ErrorCode.TOKEN_CANNOT_BE_EMPTY.getCode(),
                ErrorCode.TOKEN_CANNOT_BE_EMPTY.getMessage()));
        }
        try {
            // 判断是否是有效的token
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            // 取出用户身份
            String principal = MapUtil.getStr(additionalInformation, "user_name");
            //获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("principal",principal);
            jsonObject.put("authorities",authorities);
            // 给header里面添加值
            String base64 = EncryptUtil.encodeUTF8StringBase64(jsonObject.toJSONString());
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("json-token", base64).build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        } catch (InvalidTokenException ignored) {
            log.info("无效的token: {}", token);
            throw new BusinessException(HttpStatus.NOT_FOUND, new ApiError(ErrorCode.INVALID_TOKEN.getCode(),
                ErrorCode.INVALID_TOKEN.getMessage()));
        }

    }

    /**
     * 获取token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }
    @Override
    public int getOrder() {
        return 0;
    }
}
