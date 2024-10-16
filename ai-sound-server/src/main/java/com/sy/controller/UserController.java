package com.sy.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sy.common.config.RedisConfig;
import com.sy.common.constants.LoginType;

import com.sy.common.util.RedisUtils;

import com.sy.domain.dto.RewardsInfoVo;
import com.sy.domain.dto.UserTokenDto;
import com.sy.domain.request.InitRequest;
import com.sy.domain.response.CommonCode;
import com.sy.domain.response.ResponseResult;
import com.sy.domain.response.ResponseUtil;
import com.sy.service.AppUserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

import static com.sy.common.constants.Constants.LANGUAGE_HEADER;


/**
 * 手机端用户请求控制器
 *
 * @create: 2024-06-20 10:40
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private AppUserService appUserService;
    /**
     * 邮箱
     */
    private final static String EMAIL = "bonifacebenjamin5@gmail.com";

    @Resource
    private MeterRegistry meterRegistry;

    /**
     * 图片后缀
     */
    private static final String rewardsImage = "reward.png";

    @Value("${score.wall.image-url}")
    private String imageUrl;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private RedisConfig redisConfig;


    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public ResponseResult<Object> login(@RequestBody InitRequest initRequest, HttpServletRequest httpServletRequest) {
        Counter.builder("metrics.request.count").tags("login", "true").register(meterRegistry).increment();
        try {
            String language = httpServletRequest.getHeader(LANGUAGE_HEADER);
            // 生成令牌
            Map<String, Object> initializeMap = appUserService.initialize(initRequest);
            if (Objects.isNull(initializeMap)) {
                return ResponseUtil.createResponse(CommonCode.FAIL, httpServletRequest);
            }
            //获取字段对应信息
            UserTokenDto userTokenDto = new UserTokenDto();
            userTokenDto.setToken(initializeMap.get("token").toString());
            if (LoginType.VISITOR.equals(initRequest.getLoginType())) {
                userTokenDto.setIsVisitor(Boolean.TRUE);
            } else {
                userTokenDto.setIsVisitor(Boolean.FALSE);
            }
            String userId = initializeMap.get("userId").toString();
            RewardsInfoVo result = appUserService.rewards(userId, language);
            userTokenDto.setHasPaypal(result.getHasPaypal());
            userTokenDto.setHasBank(result.getHasBank());
            userTokenDto.setUserId(result.getUserId());
            userTokenDto.setUserHeadUrl(result.getUserHeadUrl());
            userTokenDto.setUserName(result.getUserName());
            userTokenDto.setUserLevel(result.getUserLevel());
            userTokenDto.setCoinNum(result.getCoinNum());
            userTokenDto.setIsNewUser((boolean) initializeMap.get("isNewUser"));
            userTokenDto.setLevelPictureUrl(result.getLevelPictureUrl());

            return ResponseUtil.createResponse(CommonCode.SUCCESS, httpServletRequest, userTokenDto);
        } catch (Exception e) {
            log.error("UserController login error：", e);
            return ResponseUtil.createResponse(CommonCode.FAIL, httpServletRequest);
        }
    }


    /**
     * 个人信息接口
     */
    @GetMapping(value = "/rewardsInfo")
    public ResponseResult<Object> rewards(HttpServletRequest httpServletRequest) {
        try {
            String language = httpServletRequest.getHeader(LANGUAGE_HEADER);
            RewardsInfoVo result = appUserService.rewards(null, language);
            return ResponseUtil.createResponse(CommonCode.SUCCESS, httpServletRequest, result);
        } catch (Exception e) {
            log.error("UserController rewardsInfo error：", e);
            return ResponseUtil.createResponse(CommonCode.FAIL, httpServletRequest);
        }
    }

}
