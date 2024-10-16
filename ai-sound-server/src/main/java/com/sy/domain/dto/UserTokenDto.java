package com.sy.domain.dto;

import lombok.Data;

/**
 * @author lwb
 * @description
 * @date 2024/7/29
 */
@Data
public class UserTokenDto {
    /**
     * 用户token
     */
    private String token;

    /**
     * 是否是游客
     */
    private  Boolean isVisitor;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户头像
     */
    private String userHeadUrl;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户等级
     */
    private Integer userLevel;


    /**
     * 金币数量
     */
    private Long coinNum;


    /**
     * 是否是新用户
     */
    private  Boolean isNewUser;

    /**
     * 轮盘抽奖的剩余次数
     */
    private Integer turntableTimes;

    /**
     * 等级图片
     */
    private String levelPictureUrl;

    private Boolean hasPaypal;

    private Boolean hasBank ;

}
