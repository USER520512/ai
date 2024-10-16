package com.sy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.domain.model.AppUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * app用户表 Mapper 接口
 * </p>
 *
 * @author zys
 * @since 2024-06-21
 */
@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

}
