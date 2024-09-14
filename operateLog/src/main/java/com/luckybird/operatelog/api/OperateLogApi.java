package com.luckybird.operatelog.api;

import com.luckybird.common.base.PageResult;
import com.luckybird.operatelog.api.req.OperateLogQueryReq;
import com.luckybird.operatelog.api.vo.OperateLogVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * 操作日志API
 *
 * @author 新云鸟
 */
public interface OperateLogApi {

    /**
     * 获取操作日志
     *
     * @param id 操作日志id
     * @return OperateLogVO 操作日志信息
     */
    @GetMapping("/v1/dept/{id}")
    OperateLogVO get(@PathVariable Long id);

    /**
     * 批量获取操作日志
     *
     * @param ids 操作日志id数组
     * @return List<OperateLogVO> 操作日志信息列表
     */
    @PostMapping("/v1/dept/batchGet")
    List<OperateLogVO> batchGet(@RequestBody Set<Long> ids);

    /**
     * 查询操作日志列表
     *
     * @param req 操作日志查询请求
     * @return List<OperateLogVO> 操作日志信息列表
     */
    @PostMapping("/v1/dept/list")
    List<OperateLogVO> list(@RequestBody OperateLogQueryReq req);

    /**
     * 查询操作日志分页
     *
     * @param current     当前页
     * @param rows        每页条数
     * @param searchCount 是否搜索总数
     * @param req         操作日志查询请求
     * @return PageResult<OperateLogVO> 操作日志信息分页
     */
    @PostMapping("/v1/dept/page")
    PageResult<OperateLogVO> page(
            @RequestParam(name = "current", defaultValue = "1") Long current,
            @RequestParam(name = "rows", defaultValue = "10") Long rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody OperateLogQueryReq req);
}
