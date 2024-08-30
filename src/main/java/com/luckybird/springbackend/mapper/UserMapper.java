package com.luckybird.springbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luckybird.springbackend.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author 新云鸟
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
