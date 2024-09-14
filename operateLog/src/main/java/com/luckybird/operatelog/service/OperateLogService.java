package com.luckybird.operatelog.service;

import com.luckybird.common.base.PageResult;
import com.luckybird.operatelog.api.req.OperateLogQueryReq;
import com.luckybird.operatelog.api.vo.OperateLogVO;

import java.util.List;
import java.util.Set;

/**
 * 操作日志服务
 *
 * @author 新云鸟
 */
public interface OperateLogService {
    OperateLogVO get(Long id);

    List<OperateLogVO> batchGet(Set<Long> ids);

    List<OperateLogVO> list(OperateLogQueryReq req);

    PageResult<OperateLogVO> page(OperateLogQueryReq req, Long current, Long rows, boolean searchCount);
}
