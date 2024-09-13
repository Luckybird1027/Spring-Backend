package com.luckybird.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luckybird.repository.po.OperateLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志Mapper
 *
 * @author 新云鸟
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLogPO> {
}
