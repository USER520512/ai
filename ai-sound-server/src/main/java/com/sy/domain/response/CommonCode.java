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
    public static final CommonCode SUCCESS = new CommonCode(200, "success");
    /**
     * 服务器内部错误
     */
    public static final CommonCode INTERNAL_SERVER_ERROR = new CommonCode(500, "Server internal error");
    /**
     * 操作失败
     */
    public static final CommonCode FAIL = new CommonCode(204, "Operation failed");
    /**
     * 参数错误  Parameter error
     */
    public static final CommonCode PARAM_ERROR = new CommonCode(504, "Parameter error");
    /**
     * 认证失败无法访问系统资源
     */
    public static final CommonCode UNAUTHORIZED_ERROR = new CommonCode(401, "Authentication failed and system resources cannot be accessed.");

    /**
     * 用户唯一标识为空为空
     */
    public static final CommonCode CLIENT_ID_ERROR = new CommonCode(1000, "The user's unique identifier is empty.");

    /**
     * 用户未登陆或已过期，请登陆系统后再访问！
     */
    public static final CommonCode USER_LOGIN_ERROR = new CommonCode(1002, "The user is not logged in or has expired. Please log in to the system before accessing again!");

    /**
     * 获取到的信息是空
     */
    public static final CommonCode INFO_IS_EMPTY = new CommonCode(1005, "The information obtained is empty!");

    /**
     * 宝箱空了
     */
    public static final CommonCode BOX_IS_EMPTY = new CommonCode(1006, "The treasure chest is empty");

    /**
     * 你已经打开过宝箱
     */
    public static final CommonCode BOX_IS_OPEN = new CommonCode(1016, "The treasure chest is open");


    /**
     * 今天已经签到过了
     */
    public static final CommonCode CHECK_IS_EXIST = new CommonCode(1007, "Today has been signed in");

    /**
     * 游客身份限制
     */
    public static final CommonCode  VISITOR_STATUS_ERROR = new CommonCode(1008, "Visitor status restrictions");

    /**
     * 请勿重复点击
     */
    public static final CommonCode    NETWORK_IS_OUT = new CommonCode(1015, "Please do not click repeatedly");


    /**
     * 任务数量已达到上限
     */
    public static final CommonCode   TASK_LIMIT_REACHED = new CommonCode(1017, "Task limit reached");
    /**
     * 任务不存在
     */
    public static final CommonCode    TASK_DOES_NOT_EXIST = new CommonCode(1018, "Task does not exist");

    /**
     * 任务已经终止
     */
    public static final CommonCode    TASK_HAS_BEEN_TERMINATED = new CommonCode(1019, "Task has been terminated");

    /**
     * 任务已经完成过了
     */
    public static final CommonCode    TASK_HAS_ALREADY_BEEN_COMPLETED = new CommonCode(1020, "Task has already been completed");

    /**
     * 今天已经提现过了
     */
    public static final CommonCode   ALREADY_WITHDRAWN_TODAY  = new CommonCode(1021, "Already withdrawn today");

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
