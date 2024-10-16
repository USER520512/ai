package com.sy.common.constants;

/**
 * @description: 公共常量类
 * @author: zys
 * @create: 2024-06-20 16:28
 **/
public class Constants {

    /**
     * 标点符号
     */
    public static final String SIGN_COMMA = ",";
    public static final String SIGN_BLANK = " ";
    public static final String SIGN = "";
    public static final String SIGN_SEMICOLON = ";";
    public static final String SIGN_UNDERLINE = "_";
    public static final String SIGN_MINUS = "-";
    public static final String SIGN_PERCENT = "%";
    public static final String SIGN_OBLIQUE_LINE = "/";
    public static final String SIGN_DOUBLE_OBLIQUE_LINE = "//";
    public static final String SIGN_BACKSLASH = "\\";
    public static final String SIGN_COLON = ":";
    public static final String SIGN_POINT = ".";
    public static final String SIGN_QUERY = "?";
    public static final String SIGN_AND = "&";
    public static final String SIGN_AT = "@";
    public static final String SIGN_EQUALS = "=";
    public static final String SIGN_LT = "<";
    public static final String SIGN_GT = ">";
    public static final String SIGN_OR = "|";
    public static final String SIGN_BRACKET_L = "[";
    public static final String SIGN_BRACKET_R = "]";
    public static final String SIGN_SPLIT = "___";
    public static final String SIGN_ASTERISK = "*";
    public static final String SIGN_COLON_DOUBLE = "::";
    public static final String SIGN_DOUBLE_VERTICAL_LINE = "||";
    public static final String SIGN_LINEFEED = "\n";
    public static final String SIGN_ARROW = "->";
    public static final String SIGN_CAESURA = "、";
    public static final String SIGN_DOUBLE_UNDERLINE = "__";
    public static final String SIGN_BLANK_MINUS = " - ";
    public static final String STR_EMPTY = "";
    public static final String SIGN_DOUBLE_MINUS = "--";
    public static final String _ZIP_ = "_zip_";

    public static final String TEMP_SUFFIX = "_tmp";
    public static final String INI_SUFFIX = "ini";
    public static final String TS_SUFFIX = "ts";
    public static final String TXT_SUFFIX = "txt";

    public static final String UTF_8 = "UTF-8";
    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";
    public static final String REGION_NAME_CHINA = "中国";
    public static final String STR_ALL = "all";
    public static final String STRING_MINUS_1 = "-1";
    public static final String STRING_MINUS_0 = "0";
    public static final String STRING_1 = "1";
    public static final Integer SUPER_ADMIN_ID = 1;
    /**
     * number
     */
    public static final Integer INTEGER_MINUS_1 = -1;
    public static final Integer INTEGER_0 = 0;
    public static final Integer INTEGER_1 = 1;
    public static final Integer INTEGER_2 = 2;
    public static final Integer INTEGER_3 = 3;
    public static final Integer INTEGER_4 = 4;
    public static final Integer INTEGER_5 = 5;
    public static final Integer INTEGER_6 = 6;
    public static final Integer INTEGER_7 = 7;
    public static final Integer INTEGER_8 = 8;
    public static final Integer INTEGER_9 = 9;
    public static final Integer INTEGER_10 = 10;
    public static final Integer INTEGER_11 = 11;
    public static final Integer INTEGER_12 = 12;
    public static final Integer INTEGER_15 = 15;
    public static final Integer INTEGER_32 = 32;
    public static final Integer INTEGER_200 = 200;

    public static final String MINUTE_30 = "30";
    public static final String HOUR_0 = "00";
    public static final String HOUR_24 = "24";
    public static final String HOUR_0_24 = "00:00 - 24:00";
    public static final String START_HOUR = "00:00";
    public static final String END_HOUR = "24:00";

    public static final Integer INTEGER_1024 = 1024;
    public static final Integer INTEGER_1000 = 1000;
    public static final Long LONG_2000 = 2000L;
    public static final Integer INTEGER_10000 = 10000;
    public static final Integer INTEGER_100000 = 100000;
    public static final int DEFAULT_BATCH_SIZE = 10000;

    public static final double DOUBLE_1 = 1.0;
    public static final Double DOUBLE_2 = 2.0;
    public static final Double DOUBLE_MINUS_1 = -1.0;
    public static final Double DOUBLE_0 = 0.0;
    public static final String STR_0 = "0";
    public static final String STR_MINUTE_1 = "-1";

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
     * 提现每日限制key
     */
    public static String CASH_OUT_KEY = "cash_out_user_key";

    /**
     * 抽奖转盘本日限制key
     */
    public static String TURNTABLE_KEY = "turntable_user_key";

    /**
     * 广告限制次数key
     */
    public static String AD_LIMIT_KEY = "ad_limit_key";

    /**
     * 宝箱KEY
     */
    public static String TREASURE_KEY = "treasure_key";

    /**
     * 今日宝箱库存
     */
    public static String TREASURE_STOCK_TODAY = "treasure_stock_today";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";


    public static final String LOGIN_COIN_KEY = "login_user_key:";

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
    public static final String PLAN_CACHE_CRON = "0 0/1 * * * ?";

    /**
     * 定时任务缓存时间设置
     */
    public static final String WEIGHT_CACHE_CRON = "0 0/30 * * * ?";


    /**
     * 默认密码
     */

    public static final String DEFAULT_PASSWORD = "123456";



    /**
     * 做单类型  1每日一次 2每人多次 3每人一次
     */
    public static final String GAME_TYPE_DAILY_ONCE = "1";
    public static final String GAME_TYPE_PERSONAL_MULTIPLE = "2";
    public static final String GAME_TYPE_PERSONAL_ONCE = "3";

    /**
     * 转盘规则
     */
    public static final String  TURNTABLERULES = "1. By participating in this activity, users signi-fy their understanding and agreement\n" +
             "2. This activity is a probability based winningevent, where participants have the opportu-nity to receive rewards such as coins, surprispackages, etc\n" +
             "3. The final right to end the event belongs tothis app.";
    /**
     * 隐私政策
     */
    public static final String PRIVACY_POLICY = "https://trendnetworktech.com/privacy/index.html";


    /**
     * PAYPAL支付宝规则
     */
    public static final String  PAYPALRULES = "(1) Make sure your app's version isthe latest version.\n" +
            "(2 )Please confirm that the PayPalaccount is correcct, otherwise thecash will be unsucc-essful or thewrong account will be charged, andyou are responsible for it.\n" +
            "(3) Please confirm the exchangeamount .After sending, the amountcannot be changed anymore.\n" +
            "(4)Exchange process will becompleted within 3 working days.";


    /**
     * 金币转美元的汇率
     */
    public static final String  COINCONVERTDOLLAR  = "7000";

    /**
     * 金币转印尼的汇率
     */
    public static final String  COINCONVERTINDONESIA  = "0.47";


    /**
     *  游客用户默认值
     */
    public static final String  VISITOR_USER_NAME  = "VISITOR";

    /**
     * 美元符号
     */
    public static final String  DOLLAR_SIGN  = "$";
    /**
     * 印尼符号
     */
    public static final String  INDONESIA_SIGN  = "Rp";

    /**
     * 接口请求头语言字段
     */
    public static final String  LANGUAGE_HEADER  = "Accept-Language";

    /**
     * 印尼语言请求头值
     */
    public static final String  INDONESIA_LANGUAGE_HEADER_VALUE  = "in";
}
