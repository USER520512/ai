package com.sy.domain.response;

import lombok.Data;

/**
 * @description: 公共响应code
 * @author: zys
 * @create: 2024-06-20 10:51
 **/
@Data
public class CommonCode implements IResultCode {

    /**
     * 成功
     */
    public static final CommonCode SUCCESS = new CommonCode(200, "成功");

    /**
     * 服务器内部错误
     */
    public static final CommonCode INTERNAL_SERVER_ERROR = new CommonCode(500, "服务器内部错误");
    /**
     * 操作失败
     */
    public static final CommonCode FAIL = new CommonCode(204, "操作失败");
    /**
     * 参数错误
     */
    public static final CommonCode PARAM_ERROR = new CommonCode(504, "参数错误");
    /**
     * 认证失败无法访问系统资源
     */
    public static final CommonCode UNAUTHORIZED_ERROR = new CommonCode(401, "认证失败无法访问系统资源");

    /**
     * 用户唯一标识为空为空
     */
    public static final CommonCode CLIENT_ID_ERROR = new CommonCode(1000, "用户唯一标识为空为空");

    /**
     * 用户未登陆或已过期，请登陆系统后再访问！
     */
    public static final CommonCode USER_LOGIN_ERROR = new CommonCode(1002, "用户未登陆或已过期，请登陆系统后再访问！");

    private int code;

    private String message;

    public CommonCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public CommonCode setMessage(String message) {
        this.message = message;
        return this;
    }

}
