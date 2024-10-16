package com.sy.common.enums;

import lombok.Getter;

/**
 * @description: 删除枚举
 * @author: zys
 * @create: 2024-06-21 15:05
 **/
@Getter
public enum DelFlagEnum {

    /**
     * 正常
     */
    ENABLE("0"),
    /**
     * 删除
     */
    DEL("2");

    private final String code;

    DelFlagEnum(String code) {
        this.code = code;
    }

}
