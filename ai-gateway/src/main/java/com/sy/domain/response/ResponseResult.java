package com.sy.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zys
 * @date 2024/6/16 18:18
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> implements Response {
    private Integer code;
    private String message;
    private T data;

    public ResponseResult(IResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ResponseResult(IResultCode resultCode, T result) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = result;
    }
}
