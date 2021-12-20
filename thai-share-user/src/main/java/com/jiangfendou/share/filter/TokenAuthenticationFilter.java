package com.jiangfendou.share.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiangfendou.share.common.ApiError;
import com.jiangfendou.share.common.BusinessException;
import com.jiangfendou.share.type.ErrorCode;
import com.jiangfendou.share.util.EncryptUtil;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * TokenAuthenticationFilter.
 * @author jiangmh
 */
//@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 解析出头当中的token
        String token = httpServletRequest.getHeader("json-token");

        if (Objects.isNull(token)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, new ApiError(ErrorCode.TOKEN_CANNOT_BE_EMPTY.getCode(),
                ErrorCode.TOKEN_CANNOT_BE_EMPTY.getMessage()));
        }
        String jsonToken = EncryptUtil.decodeUTF8StringBase64(token);
        JSONObject jsonObject = JSON.parseObject(jsonToken);
        // 用户信息
        String principal = jsonObject.getString("principal");
        // 用户权限
        JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");

        String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
        // 将身份信息和权限填充到用户身份的token对象中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(principal, null,
                AuthorityUtils.createAuthorityList(authorities));
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        // 将usernamePasswordAuthenticationToken填充到安全上下文
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
