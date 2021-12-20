package com.jiangfendou.share.config;

import com.jiangfendou.share.filter.LoginFailureHandler;
import com.jiangfendou.share.filter.LoginSuccessHandler;
import com.jiangfendou.share.filter.MyLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

/**
 * web访问安全策略配置
 * @author jiangmh
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private MyLogoutHandler logoutHandler;

    /**
     * 认证管理
     * */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全拦截机制（最重要）
     * */
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.
//            csrf().
//            disable().
//            authorizeRequests().
//            // 访问含有login的路径的时候必须有 admin 的权限
////            antMatchers("/**/login*").hasAuthority("admin").
//            // 除了/**/login* 其他的请求路径都需要认证
//            antMatchers("/**/login*").
//            permitAll().
//            anyRequest().
//            authenticated().
//            and().
//            // 允许表单提交
//            formLogin();
//    }

    /**
     * 安全拦截机制（最重要）
     * */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf()
            .disable()
            .formLogin()
            .loginProcessingUrl("/login")
            // 登录成功
            .successHandler(loginSuccessHandler)
            // 登录失败
            .failureHandler(loginFailureHandler).permitAll()
            .and()
            // 注销成功
            .logout().logoutSuccessHandler(logoutHandler)
            .and()
            // 未登录请求资源
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }
}
