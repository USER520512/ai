package com.sy.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.common.constants.LoginType;
import com.sy.common.enums.StatusEnum;
import com.sy.common.util.StringUtils;
import com.sy.domain.dto.*;
import com.sy.domain.model.AppUser;
import com.sy.domain.model.LoginUser;

import com.sy.domain.request.InitRequest;
import com.sy.filter.LoginUserHolder;
import com.sy.mapper.AppUserMapper;
import com.sy.service.AppUserService;
import com.sy.service.ITokenService;

import com.sy.strategy.login.UserLoginStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * app用户表 服务实现类
 * </p>
 *
 * @author zys
 * @since 2024-06-21
 */
@Service
@Slf4j
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    @Resource
    private ITokenService tokenService;

    @Resource
    private UserLoginStrategyFactory userLoginStrategyFactory;

    @Resource
    private MessageSource messageSource;


    @Value("${score.wall.image-url}")
    private String imageUrl;


    private static final String USER_LEVEL_PREFIX = "level_";

    private static final String USER_LEVEL_URL_PREFIX = ".webp";

    /**
     * 登录验证
     *
     * @param initRequest 登录请求体
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> initialize(InitRequest initRequest) {
        //用户验证,记录用户基本信息
        Map<String, Object> map = userLoginStrategyFactory.getStrategy(initRequest.getLoginType()).login(initRequest);
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(map.get("userId").toString());
        loginUser.setClientId(initRequest.getClientId());
        if (LoginType.VISITOR.equals(initRequest.getLoginType())){
            String token = tokenService.createToken(loginUser, Boolean.TRUE);
            map.put("token", token);
            return map;
        }
        String token = tokenService.createToken(loginUser, Boolean.FALSE);
        map.put("token", token);
        return map;
    }

    @Override
    public RewardsInfoVo rewards(String userId,String language) {
        if (StringUtils.isEmpty(language)){
            language = "en";
        }
        Locale locale = Locale.forLanguageTag(language);
        String userName = messageSource.getMessage("userName", null, locale);
        LambdaUpdateWrapper<AppUser> wrapper = new LambdaUpdateWrapper();
        wrapper.eq(AppUser::getUserId, StringUtils.isEmpty(userId)?LoginUserHolder.getUserId():userId)
                .eq(AppUser::getStatus, StatusEnum.ENABLE.getStatus());
        AppUser existAppUser = this.getOne(wrapper);
        if (Objects.isNull(existAppUser)) {
            throw new RuntimeException("The user does not exist");
        }
        RewardsInfoVo rewardsInfoDto = new RewardsInfoVo();
        //检查是否是游客
        if (StringUtils.isEmpty(existAppUser.getBindCode())){
            rewardsInfoDto.setUserName(userName);
        }else {
            rewardsInfoDto.setUserName(existAppUser.getUserName());
        }
        if (existAppUser.getUserLevel()<10){
            String levelPictureUrl =imageUrl+ USER_LEVEL_PREFIX + existAppUser.getUserLevel() + USER_LEVEL_URL_PREFIX;
            rewardsInfoDto.setLevelPictureUrl(levelPictureUrl);
        }else {
            String levelPictureUrl =imageUrl+ USER_LEVEL_PREFIX + 10 + USER_LEVEL_URL_PREFIX;
            rewardsInfoDto.setLevelPictureUrl(levelPictureUrl);
        }
        rewardsInfoDto.setUserLevel(existAppUser.getUserLevel());
        rewardsInfoDto.setUserId(existAppUser.getUserId());
        rewardsInfoDto.setUserHeadUrl(existAppUser.getUserHeadUrl());
        return rewardsInfoDto;
    }
}
