package com.sy.common.enums;

import lombok.Getter;

/**
 * @description: 状态枚举
 * @author: zys
 * @create: 2024-06-21 15:05
 **/
@Getter
public enum StatusEnum {

    /**
     * 启用
     */
    ENABLE("0"),
    /**
     * 暂停
     */
    DISABLE("1"),

    /**
     * 等待激活
     */
    WAIT_ACTIVE("2");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

}
