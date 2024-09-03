package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.req.DeptCreateReq;
import com.luckybird.springbackend.api.req.DeptQueryReq;
import com.luckybird.springbackend.api.req.DeptUpdateReq;
import com.luckybird.springbackend.api.vo.DeptVO;
import com.luckybird.springbackend.common.base.PageResult;

import java.util.List;
import java.util.Set;

/**
 * 部门服务实现
 *
 * @author 新云鸟
 */
public class DeptServiceImpl implements DeptService {
    @Override
    public DeptVO get(Long id) {
        return null;
    }

    @Override
    public List<DeptVO> batchGet(Set<Long> ids) {
        return null;
    }

    @Override
    public DeptVO create(DeptCreateReq req) {
        return null;
    }

    @Override
    public DeptVO update(Long id, DeptUpdateReq req) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<DeptVO> list(DeptQueryReq req) {
        return null;
    }

    @Override
    public PageResult<DeptVO> page(DeptQueryReq req, Long current, Long pageSize, boolean searchCount) {
        return null;
    }
}
