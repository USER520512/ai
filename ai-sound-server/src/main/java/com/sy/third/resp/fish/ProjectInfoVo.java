package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectInfoVo implements Serializable  {
    private Integer id;

    /**
     * 项目fake_id
     */
    private String fakeId;

    /**
     * 企业id，对应user表的parent_uuid
     */
    private String enterpriseId;

    /**
     * 可见范围（1：对所有人可见，2：指定用户可见）
     */
    private Integer scope;

    /**
     * 项目名称
     */
    private String title;

    /**
     * 项目绑定用户id，多个以逗号分隔
     */
    private String bindUserId;

    /**
     * 项目绑定用户，多个以逗号分隔
     */
    private String bindUserName;

    /**
     * 项目负责人id，多个以逗号分隔
     */
    private String chargePersonId;

    /**
     * 项目负责人，多个以逗号分隔
     */
    private String chargePersonName;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

    private static final long serialVersionUID = 1L;
    /**
     * 删除状态
     * 1 已删除 0 未删除

     */
    private Boolean isDelete;
}
