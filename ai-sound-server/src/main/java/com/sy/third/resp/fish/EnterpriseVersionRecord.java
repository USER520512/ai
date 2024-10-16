package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;

/**
 * enterprise_version_record
 * @author 
 */
@Data
public class EnterpriseVersionRecord implements Serializable {
    private Integer id;

    /**
     * 版本唯一标识
     */
    private String versionCode;

    /**
     * 主账号uuid
     */
    private String parentUuid;

    /**
     * 系统子账号数
     */
    private Integer systemAccount;
    /**
     * 授权广告主数
     */
    private Integer busAuthAdvertiser;

    /**
     * 有效期
     */
    private Integer ableDay;

    /**
     * 上传素材数
     */
    private Integer uploadMaterial;

    /**
     * 菜单列表(多个以逗号分隔)
     */
    private String menus;

    /**
     * 会员开始时间
     */
    private String startTime;

    /**
     * 会员结束时间
     */
    private String endTime;


    /**
     * 是否删除（0：否，1：是）
     */
    private Integer isDelete;

    /**
     * 是否付费（1：是，0：否）
     */
    private Integer isPay;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 更新时间
     */
    private String gmtModified;

    private static final long serialVersionUID = 1L;

    /**
     * 版本类型（0 免费版，1、2、3 为付费版）
     */
    private Integer versionType;

    /**
     * 是否免费版(true 是，false 否)
     */
    private Boolean isFree;
}