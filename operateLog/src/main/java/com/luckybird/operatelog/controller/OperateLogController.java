package com.luckybird.operatelog.controller;

import com.luckybird.common.base.PageResult;
import com.luckybird.operatelog.api.OperateLogApi;
import com.luckybird.operatelog.api.req.OperateLogQueryReq;
import com.luckybird.operatelog.api.vo.OperateLogVO;
import com.luckybird.operatelog.service.OperateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 操作日志
 *
 * @author 新云鸟
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class OperateLogController implements OperateLogApi {

    private final OperateLogService operateLogService;

    /**
     * 获取操作日志
     *
     * @param id 操作日志id
     * @return OperateLogVO 操作日志信息
     */
    @Override
    @GetMapping("/v1/operateLog/{id}")
    public OperateLogVO get(@PathVariable Long id) {
        return operateLogService.get(id);
    }

    /**
     * 批量获取操作日志
     *
     * @param ids 操作日志id数组
     * @return List<OperateLogVO> 操作日志信息列表
     */
    @Override
    @PostMapping("/v1/operateLog/batchGet")
    public List<OperateLogVO> batchGet(@RequestBody Set<Long> ids) {
        return operateLogService.batchGet(ids);
    }

    /**
     * 查询操作日志列表
     *
     * @param req 操作日志查询请求
     * @return List<OperateLogVO> 操作日志信息列表
     */
    @Override
    @PostMapping("/v1/operateLog/list")
    public List<OperateLogVO> list(@RequestBody OperateLogQueryReq req) {
        return operateLogService.list(req);
    }

    /**
     * 查询操作日志分页
     *
     * @param current     当前页
     * @param rows        每页条数
     * @param searchCount 是否搜索总数
     * @param req         操作日志查询请求
     * @return PageResult<OperateLogVO> 操作日志信息分页
     */
    @Override
    @PostMapping("/v1/operateLog/page")
    public PageResult<OperateLogVO> page(
            @RequestParam(name = "current", defaultValue = "1") Long current,
            @RequestParam(name = "rows", defaultValue = "10") Long rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody OperateLogQueryReq req) {
        return operateLogService.page(req, current, rows, searchCount);
    }
}
