package com.sy.third.resp.fish;

import lombok.Data;

@Data
public class RoleModule {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 模块id
     */
    private Integer moduleId;

    /**
     * 模块标识
     */
    private String moduleCode;

    /**
     * 数据隔离级别：1：企业，2：部门，3：个人
     */
    private Integer dataLimit;
}
