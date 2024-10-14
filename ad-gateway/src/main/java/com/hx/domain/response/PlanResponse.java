package com.hx.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 计划响应实体类
 *
 * @author zys
 * @since 2024-06-25
 */
@Getter
@Setter
@Accessors(chain = true)
public class PlanResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 广告类型:1-开屏 2-插屏
     */
    private String adType;

    /**
     * 计划唯一标识采用uuid
     */
    private String reqId;

    /**
     * 素材响应集合
     */
    private List<MaterialResponse> materialResponses;

}
