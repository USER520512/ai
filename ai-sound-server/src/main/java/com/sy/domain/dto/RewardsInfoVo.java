package com.sy.domain.dto;

import lombok.Data;

/**
 * @author lwb
 * @description
 * @date 2024/7/29
 */
@Data
public class RewardsInfoVo {
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
     * 等级图片
     */
    private String levelPictureUrl;

    private Boolean hasPaypal = Boolean.FALSE;

    private Boolean hasBank = Boolean.FALSE ;
}
