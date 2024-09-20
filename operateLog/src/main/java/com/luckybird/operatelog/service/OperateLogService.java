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

    /**
     * 获取操作日志
     *
     * @param id 日志ID
     * @return 操作日志信息
     */
    OperateLogVO get(Long id);

    /**
     * 批量获取操作日志
     * @param ids 日志ID集合
     * @return 操作日志信息集合
     */
    List<OperateLogVO> batchGet(Set<Long> ids);

    /**
     * 获取操作日志列表
     * @param req 查询请求
     * @return 操作日志列表
     */
    List<OperateLogVO> list(OperateLogQueryReq req);

    /**
     * 分页获取操作日志列表
     * @param req 查询请求
     * @param current 页码
     * @param pageSize 页大小
     * @param searchCount 是否搜索总数
     * @return 分页结果
     */
    PageResult<OperateLogVO> page(OperateLogQueryReq req, Long current, Long pageSize, boolean searchCount);
}
