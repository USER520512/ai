package com.sy.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 初始化请求
 * @author: zys
 * @create: 2024-06-20 10:24
 **/
@Data
public class InitRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID app登录时传入 必填
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 设备标识
     */
    @JsonProperty("ga_id")
    private String gaId;


    private String pkg;

    /**
     * Android 必填
     */
    private String imei;

    /**
     * 客户端类型 1安卓 2ios
     */
    private String os;

    /**
     * 系统版本
     */
    private String osv;

    /**
     * 分辨率宽
     */
    private Integer width;

    /**
     * 分辨率高
     */
    private Integer height;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 0: 未知; 1: 蜂窝数据网络未知; 2: wifi; 3: 2G; 4: 3G; 5: 4G; 6: 5G
     */
    private String net;

    /**
     * mac 地址
     */
    private String mac;


    @JsonProperty("app_key")
    private String appKey;

    /**
     * 毫秒为单位的当前本地时间
     */
    private Long ts;

    /**
     * 移动用户识别码
     */
    private String imsi;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用版本号
     */
    @JsonProperty("version_code")
    private String versionCode;

    /**
     * 应用版本名称
     */
    @JsonProperty("version_name")
    private String versionName;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 制造商
     */
    private String manufacturer;
    /**
     * 内存
     */
    private String memory;

    /**
     * 谷歌userName
     */
    private String userName;

    /**
     * 谷歌userEmail
     */
    private String userEmail;

    /**
     * 谷歌用户ID
     */
    private String googleUserId;


    /**
     * 谷歌用户头像
     */
    private String googleUserHeadImg;

    /**
     * 登录类型
     */
    private String  loginType;

    /**
     * 密码
     */
    private String password;


}
