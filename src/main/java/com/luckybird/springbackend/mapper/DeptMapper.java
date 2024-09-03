package com.luckybird.springbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luckybird.springbackend.po.DeptPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门Mapper
 *
 * @author 新云鸟
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptPO> {
}
