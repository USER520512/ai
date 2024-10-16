package com.sy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.domain.dto.RewardsInfoVo;
import com.sy.domain.model.AppUser;
import com.sy.domain.request.InitRequest;

import java.util.Map;

/**
 * <p>
 * app用户表 服务类
 * </p>
 *
 * @author zys
 * @since 2024-06-21
 */
public interface AppUserService extends IService<AppUser> {

    /**
     * 初始化用户登录信息
     *
     * @param initRequest 登录请求体
     */
    Map<String, Object> initialize(InitRequest initRequest);
    /**
     * 获取用户信息
     * @return
     */
    RewardsInfoVo rewards(String userId,String language);

}
