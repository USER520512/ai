package com.sy.third.resp.fish;

import lombok.Data;

import java.util.List;

@Data
public class ProjectInfoDto {
    private List<ProjectInfoVo> data;
    private Integer total;
}
