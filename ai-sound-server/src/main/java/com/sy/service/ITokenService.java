package com.sy.service;

import com.sy.domain.model.LoginUser;

/**
 * @description: Token服务设置
 * @author: zys
 * @create: 2024-06-21 14:50
 **/
public interface ITokenService {

    /**
     * 生成token
     *
     * @param loginUser
     */
    String createToken(LoginUser loginUser,Boolean isRemember);

}
