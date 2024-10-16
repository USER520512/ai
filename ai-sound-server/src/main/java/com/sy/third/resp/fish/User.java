package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;

/**
 * user
 */
@Data
public class User implements Serializable {
    /**
     * 效果广告用户表
     */
    private Long id;

    /**
     *  主站用户id
     */
    private Long mainUserId;

    /**
     * 效果广告用户唯一无意义标识
     */
    private String uuid;

    /**
     * 新榜用户体系nr_id唯一标识
     */
    private String nrId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String headImgUrl;

    /**
     * 是否可用[0:可用；1:不可用]
     */
    private Integer status;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门编号[0:标识该用户不属于某个部门]
     */
    private Integer deptId;

    /**
     * 父级uuid(若非子账号则parent_uuid就是uuid)
     */
    private String parentUuid;

    /**
     * 会员等级[0:免费会员;1:试用会员;2:商务版;3:豪华版]
     */
    private Integer vipLevel;

    /**
     * 会员uuid(对应相应会员规则)
     */
    private String vipUuid;

    /**
     * 会员开始时间
     */
    private String vipStartTime;

    /**
     * 会员结束时间
     */
    private String vipEndTime;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

    private static final long serialVersionUID = 1L;
}