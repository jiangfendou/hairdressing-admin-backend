package com.jiangfendou.share.config;

import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author jiangmh
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 将客户端的信息存储到数据库
     * */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService)clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 1、授权码模式
     * 获取授权码url：http://localhost:8082/user/oauth/authorize?client_id=share&response_type=code&scope=all&redirect_uri=http://www.baidu.com
     * 获取token的url：http://localhost:8082/user/oauth/token
     * 参数：
     *      client_id:share
     *      client_secret:secret
     *      grant_type:authorization_code
     *      code:O2nSsv
     *      redirect_uri:https://www.baidu.com
     * 使用场景：微信账号登录。
     * 2、极简模式
     *  输入url直接获取token
     *  url：http://localhost:8082/user/oauth/authorize?client_id=share&response_type=token&scope=all&redirect_uri=https://www.baidu.com
     * 使用场景：单页面应用，通过前端传递token
     * 3、账号密码模式
     *  请求url：http://localhost:8082/user/oauth/token
     *  参数：
     *      client_id:share
     *      client_secret:secret
     *      grant_type:password
     *      username:user1
     *      password:123456
     * 使用场景：客户端是自己开发的完全信任。
     * 4、客户端模式
     *  请求的url：http://localhost:8082/user/oauth/token
     *  参数：
     *      client_id:share
     *      client_secret:secret
     *      grant_type:client_credentials
     * 使用场景：内部系统使用。
     * /

    /**
     * 定义客户端详细信息服务的配置器。客户详细信息可以初始化，或者可以引用现有的 store
     * 将ClientDetailsServiceConfigurer（从您的回调AuthorizationServerConfigurer）可以用来在内存或JDBC实现客户的细节服务来定义的。
     * 客户端的重要属性是
     * clientId:(必需）客户端ID。
     * secret:(可信客户端需要）客户端密钥（如果有）。
     * scope：客户受限的范围。如果范围未定义或为空（默认值），则客户端不受范围限制。
     * authorizedGrantTypes：授权客户端使用的授权类型。默认值为空。
     * authorities：授予客户端的权限（常规Spring Security权限）。
     * 客户端详细信息可通过直接访问底层存储（例如 JdbcClientDetailsService 用例中的数据库表）或者通过 ClientDetailsManager 接口（ClientDetailsService 也能实现这两种实现），可以在正在运行的应用程序中更新客户端详细信息。
     */

    /**
     * /oauth/authorize     授权端点
     * /oauth/token         令牌断点
     * /oauth/confirm-access用户确认授权提交端点
     * /auth/error          授权服务错误信息断电
     * /auth/check_token    用户资源服务访问的令牌解析断电
     * /oauth/token_key     提供公有密钥的端点，如果你使用jwt令牌的话
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 令牌管理服务
     * */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(tokenStore);
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        // 令牌默认有效时间2小时
        defaultTokenServices.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);

        return defaultTokenServices;
    }

    /**
     * 设置授权码模式的授权码如何存取，暂时采用内存的方式
     * */
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices(){
//        return new InMemoryAuthorizationCodeServices();
//    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 令牌访问端点
     * */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            // 密码模式需要
            .authenticationManager(authenticationManager)
            // 令牌管理服务
            .authorizationCodeServices(authorizationCodeServices)
            // 允许post提交
            .tokenServices(tokenServices())
            .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 令牌访问端点安全策略
     * */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            // oauth/token_key公开
            .tokenKeyAccess("permitAll()")
            // oauth/check_key公开
            .checkTokenAccess("permitAll()")
            // 表单认证，申请令牌
            .allowFormAuthenticationForClients();
    }


}
