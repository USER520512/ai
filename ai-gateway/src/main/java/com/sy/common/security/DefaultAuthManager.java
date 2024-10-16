package com.sy.common.security;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sy.common.config.RedisConfig;
import com.sy.common.constants.Constants;
import com.sy.common.util.RedisUtils;
import com.sy.domain.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author zys
 * @description 执行认证核心方法
 * @date 2024/7/10
 */
@Slf4j
@Component
public class DefaultAuthManager implements ReactiveAuthenticationManager {

    @Resource
    private RedisConfig redisConfig;

    @Autowired
    private RedisUtils redisUtils;

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 500 * 60 * 1000L;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String tokenString = (String) authentication.getPrincipal();
        String jwtToken = getJwtToken(tokenString);
        Map<String, Object> authResult =  authToken(jwtToken);
        return Mono.just(authentication).map(auth -> new UsernamePasswordAuthenticationToken(authResult, null, null));
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
    private Map<String, Object> authToken(String jwtToken) {
        log.info("[gateway] jwtToken = {}", jwtToken);
        try {
            //获取token
            Claims claims = parseToken(jwtToken);
            if (ObjectUtils.isEmpty(claims)) {
                return null;
            }
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            Object object = redisUtils.getObject(redisConfig.userRedisTemplate(), userKey);
            if (ObjectUtils.isEmpty(object)) {
                return null;
            }
            LoginUser loginUser = JSONObject.parseObject(object.toString(), LoginUser.class);
            if (ObjectUtils.isEmpty(loginUser)) {
                return null;
            }
            //token 续约
            verifyToken(loginUser);
            return new HashMap<>(claims);
        } catch (Exception e) {
            log.error("DefaultAuthManager authToken  error:{}", e);
            return null;
        }
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    private void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisUtils.setObject(redisConfig.userRedisTemplate(), userKey, JSON.toJSONString(loginUser), expireTime, TimeUnit.MINUTES);
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
