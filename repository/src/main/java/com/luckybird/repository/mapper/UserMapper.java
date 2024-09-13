package com.luckybird.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luckybird.repository.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author 新云鸟
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
