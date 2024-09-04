package com.luckybird.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luckybird.user.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author 新云鸟
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
