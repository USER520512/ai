package com.sy.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * app用户表
 * </p>
 *
 * @author zys
 * @since 2024-06-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sc_app_user")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userHeadUrl;

    /**
     * 谷歌用户ID
     */
    private String googleUserId;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 客户端唯一标识ID
     */
    private String clientId;

    /**
     * 设备标识
     */
    private String gaId;

    /**
     * 应用包名，例：com.test.xx
     */
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

    /**
     * app唯一标识，后端生成密钥
     */
    private String appKey;

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
    private String versionCode;

    /**
     * 应用版本名称
     */
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
     * 系统其他应用包名 逗号隔开
     */
    private String pkgs;

    /**
     * 最后登录IP地址
     */
    private String loginIp;

    private String bindCode;

    /**
     * 状态：0-正常，1-停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近一次修改时间
     */
    private Date updateTime;

    /**
     * 最后访问时间
     */
    private Date loginTime;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;


    /**
     * 用户邮箱
     */
    private String userEmail;


}
