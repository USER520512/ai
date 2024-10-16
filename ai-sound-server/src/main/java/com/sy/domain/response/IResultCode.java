package com.sy.domain.response;

/**
 * 定义公共响应
 *
 * @author zys
 */
public interface IResultCode {
    /**
     * 操作代码
     *
     * @return
     */
    int code();

    /**
     * 提示信息
     *
     * @return
     */
    String message();

}
