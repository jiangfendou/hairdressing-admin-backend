package com.jiangfendou.share.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author jiangmh
 */
@Configuration
public class TokenConfig {

    @Value("${signing.key}")
    private String signingKey;

    /**
     * 令牌存储策略
     * */
//    @Bean
//    public TokenStore tokenStore() {
//        // 内存方式生成普通令牌
//        return new InMemoryTokenStore();
//    }

    @Bean
    public TokenStore tokenStore() {
        // 内存方式生成普通令牌
        return new JwtTokenStore(accessConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }


}
