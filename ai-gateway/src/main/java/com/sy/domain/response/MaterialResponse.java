package com.sy.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 素材响应实体类
 *
 * @author zys
 * @since 2024-06-25
 */
@Getter
@Setter
@Accessors(chain = true)
public class MaterialResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 素材标题
     */
    private String title;

    /**
     * 素材类型:1-视频 2-图片
     */
    private String type;

    /**
     * 素材地址
     */
    private String url;

    /**
     * 时长,单位为秒，图片为0
     */
    private Integer duration;

    /**
     * 素材的宽
     */
    private Integer width;

    /**
     * 素材的高
     */
    private Integer height;

    /**
     * 跳转类型 1.H5
     */
    private String jumpType;

    /**
     * 跳转链接
     */
    private String jumpLink;

}
