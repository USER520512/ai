package com.sy.common.enums;

import lombok.Getter;

import static com.sy.common.constants.Constants.INTEGER_0;
import static com.sy.common.constants.Constants.SIGN_OBLIQUE_LINE;

/**
 * @description: 接口对应关系枚举
 * @author: zys
 * @create: 2024-06-21 15:05
 **/
@Getter
public enum UrlEnum {

    /**
     * 广告投放请求接口
     */
    GET_AD("getAd", "trK59uEr"),

    /**
     * 广告投放展示接口
     */
    GET_SHOW("getShow", "WrHitBWq"),

    /**
     * 监控上报接口
     */
    UPDATE_REPORT("updateReport", "CAcqIx0P"),

    /**
     * 监控上报接口
     */
    INITIALIZE("initialize", "3gwYC4lH"),

    INFO("info", "2EIUiGAS"),

    CHECK("check", "xCDo3ds"),

    PROGRESS("progress", "w3NJ8MKL"),

    TOTAL("total", "vgc9STVg"),

    INCREASE("increase", "N1pMr22n"),

    LIST("list", "7jJQSwJR"),

    LOGIN("login", "vs1ta67Z"),

    REWARDS_INFO("rewardsInfo", "Jv2oQSWZ"),

    CONTACT("contact", "WP4Wm+zL"),

    GET_BIND_CODE("getBindCode", "pW69sCt3"),

    GET_INVITATIONS("getInvitations", "daP6pXgA"),

    UPGRADE_INFO("upgradeInfo", "DeXVVu3p"),

    UPGRADE("upgrade", "gfkVtY+i"),

    BIND("bind", "SO0S0iqn"),

    LOAD_LIST("loadList", "mhpxSbW"),

    RETURN("return", "BvApBlFJ"),


    GET_DEFAULT_ACCOUNT("getDefaultAccount", "man3xbf"),


    GET_THRESHOLDS("getThresholds", "b0g+k11H"),


    SUBMIT("submit", "xl63yyIo"),


    GET_INFO_PROMPT("getInfoPrompt", "PR6TCohk"),


    CONFIG("config", "jHkGKw7t"),

    GET_TURNTABLE("getTurntable", "Bf7IuptR"),


    GET_TREASURE("getTreasure", "S19fH+XQ"),

    OPENBOX("openBox", "UuHSuq2m"),

    REDOUBLE("redouble", "7trQkTp2"),

    SAVE_BANK_INFO("saveBankInfo", "S9LyN0ki"),

    GET_DEFAULT_BANK("getDefaultBank", "GtoQ49uh"),

    REFRESH_TASK("refreshTask", "pIB6elVs");



    private final String name;

    private final String encrypt;

    UrlEnum(String name, String encrypt) {
        this.name = name;
        this.encrypt = encrypt;
    }

    public static String getUrlName(String encrypt) {
        for (UrlEnum value : UrlEnum.values()) {
            if (encrypt.contains(value.getEncrypt())) {
                return encrypt.substring(INTEGER_0, encrypt.lastIndexOf(SIGN_OBLIQUE_LINE)) + SIGN_OBLIQUE_LINE + value.getName();
            }
        }
        return encrypt;
    }

}
