package com.sy.service.impl;


import com.alibaba.fastjson2.JSON;
import com.sy.common.config.RedisConfig;
import com.sy.common.constants.Constants;
import com.sy.common.util.RedisUtils;
import com.sy.domain.model.AppUser;
import com.sy.domain.model.LoginUser;
import com.sy.service.ITokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author zys
 */
@Component
public class ITokenServiceImpl implements ITokenService {

    @Resource
    private RedisConfig redisConfig;

    @Resource
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

    protected static final int VISTOR_TIME = 43200;


    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    @Override
    public String createToken(LoginUser loginUser,Boolean isRemember) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        AppUser appUser = new AppUser();
        appUser.setClientId(loginUser.getClientId());
        appUser.setUserId(loginUser.getUserId());
        loginUser.setAppUser(appUser);
        refreshToken(loginUser,isRemember);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        claims.put("clientId", loginUser.getClientId());
        claims.put("userId", loginUser.getUserId());
        return createToken(claims);
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    private void refreshToken(LoginUser loginUser,Boolean isRemember) {
        if (isRemember) {
            loginUser.setLoginTime(System.currentTimeMillis());
            loginUser.setExpireTime(loginUser.getLoginTime() + VISTOR_TIME * MILLIS_MINUTE);
            // 根据uuid将loginUser缓存
            String userKey = getTokenKey(loginUser.getToken());
            redisUtils.setObject(redisConfig.userRedisTemplate(), userKey, JSON.toJSONString(loginUser), VISTOR_TIME, TimeUnit.MINUTES);
        }else {
            loginUser.setLoginTime(System.currentTimeMillis());
            loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
            // 根据uuid将loginUser缓存
            String userKey = getTokenKey(loginUser.getToken());
            redisUtils.setObject(redisConfig.userRedisTemplate(), userKey, JSON.toJSONString(loginUser), expireTime, TimeUnit.MINUTES);
        }
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    private String getTokenKey(String token) {
        return Constants.LOGIN_TOKEN_KEY + token;
    }

}
