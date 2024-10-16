package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;

/**
 * ad_sys_company
 * @author 
 */
@Data
public class AdSysCompany implements Serializable {
    private Long id;

    /**
     * 用户id（用户uuid）
     */
    private String userId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contactUser;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 描述
     */
    private String description;

    /**
     * 行业
     */
    private String industry;

    /**
     * 职责
     */
    private String duty;
    /**
     * 客户来源， 百度/头条/还是newx啥的
     */
    private String source;
    /**
     * 删除标识（0 未删除，1已删除）
     */
    private Integer isDelete;

    /**
     * 跟进人
     */
    private String followUuid;

    /**
     * 跟进状态（0.无人跟进,1.未联系上,2.已联系,3.沟通中,4.确认意向,5.试用中,6.已付费,7.流失,8.无效客户）
     */
    private Integer followState;

    /**
     * 客服跟进时间
     */
    private String followTime;

    /**
     * 微信号
     */
    private String weixinAccount;

    /**
     * 所在部门
     */
    private String dept;

    /**
     * 职位
     */
    private String position;

    /**
     * 上传图片
     */
    private String uploadImg;

    private String gmtCreate;

    private String gmtModified;

    /**
     * 客服跟进备注
     */
    private String followRemark;

    private static final long serialVersionUID = 1L;
}