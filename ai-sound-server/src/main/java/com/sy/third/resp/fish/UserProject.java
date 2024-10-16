package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user_project
 * @author 
 */
@Data
public class UserProject implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 项目fake_id
     */
    private String fakeId;

    /**
     * 是否剔除（1：是，0：否）
     */
    private Byte isExclude;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}