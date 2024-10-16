package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;

/**
 * user_role
 * @author 
 */
@Data
public class UserRole implements Serializable {
    private Integer id;

    /**
     * 企业id，对应user表的parent_uuid
     */
    private String enterpriseId;

    /**
     * 账户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 是否可用，0：否  1：是
     */
    private Integer isUsable;

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