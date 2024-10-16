package com.sy.third.resp.fish;

import lombok.Data;

import java.io.Serializable;

/**
 * project
 */
@Data
public class Project implements Serializable {
    private Integer id;

    private String enterpriseId;

    private Integer scope;

    /**
     * 项目fake_id
     */
    private String fakeId;

    /**
     * 项目名称
     */
    private String title;

}