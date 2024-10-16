package com.sy.common.constants;

/**
 * @description: 公共常量类
 * @author: zys
 * @create: 2024-06-20 16:28
 **/
public class Constants {

    public static final String SIGN_OBLIQUE_LINE = "/";

    public static final Integer INTEGER_0 = 0;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 自动识别json对象白名单配置（仅允许解析的包名，范围越小越安全）
     */
    public static final String[] JSON_WHITELIST_STR = {"org.springframework", "com.hx"};

    /**
     * 定时任务缓存时间设置
     */
    public static final String AD_CACHE_CRON = "0 0/5 * * * ?";

    /**
     * 定时任务缓存时间设置
     */
    public static final String BASE_URL_KEY = "vHptfiRbJhvlHhGk";

}
