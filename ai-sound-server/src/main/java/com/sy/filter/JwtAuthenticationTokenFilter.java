package com.sy.filter;


import com.sy.common.config.RedisConfig;
import com.sy.common.constants.Constants;
import com.sy.common.util.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author lwb
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisConfig redisConfig;

    @Autowired
    private RedisUtils redisUtils;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String jwtToken = getJwtToken(authorization);
        if (request.getRequestURI().contains("config") ) {
            chain.doFilter(request, response);
            return;
        }
        if ( request.getRequestURI().contains("rrUrl")|| request.getRequestURI().contains("actuator/prometheus")
        || request.getRequestURI().contains("images")
        ) {
            chain.doFilter(request, response);
            return;
        }
        //过滤掉登录接口
        if (request.getRequestURI().contains("login")) {
            if (!StringUtils.hasText(jwtToken)) {
                chain.doFilter(request, response);
            }else {
                String userId = authToken(jwtToken);
                LoginUserHolder.setUserId(userId);
                chain.doFilter(request, response);
                LoginUserHolder.clearUserId();
            }
        }else {
            if (!StringUtils.hasText(jwtToken)) {
                return;
            }
            String userId = authToken(jwtToken);
            if (ObjectUtils.isEmpty(userId)) {
                return;
            }
            LoginUserHolder.setUserId(userId);
            chain.doFilter(request, response);
            LoginUserHolder.clearUserId();
        }
    }


    /**
     * 读取Jwt Token
     *
     * @return String
     */
    private String getJwtToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            return token.replace(Constants.TOKEN_PREFIX, "");
        }
        return null;
    }

    /**
     * 校验token
     *
     * @param jwtToken
     * @return
     */
    private String authToken(String jwtToken) {
        try {
            //获取token
            Claims claims = parseToken(jwtToken);
            if (ObjectUtils.isEmpty(claims)) {
                return null;
            }
            // 解析对应的权限以及用户信息
            String userId = (String) claims.get("userId");
            if (ObjectUtils.isEmpty(userId)) {
                return null;
            }
            return userId;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
