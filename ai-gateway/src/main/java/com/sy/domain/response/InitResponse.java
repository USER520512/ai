package com.sy.domain.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 登录响应
 * @author: zys
 * @create: 2024-06-20 11:23
 **/
@Data
public class InitResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录TOKEN
     */
    private String token;

    /**
     * 字段类型，用来判断取第几套字段
     */
    private String fieldType;

}
