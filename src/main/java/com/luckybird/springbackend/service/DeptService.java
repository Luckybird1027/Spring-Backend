package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.req.DeptCreateReq;
import com.luckybird.springbackend.api.req.DeptQueryReq;
import com.luckybird.springbackend.api.req.DeptUpdateReq;
import com.luckybird.springbackend.api.vo.DeptTreeVO;
import com.luckybird.springbackend.api.vo.DeptVO;
import com.luckybird.springbackend.common.base.PageResult;

import java.util.List;
import java.util.Set;

/**
 * 部门服务接口
 *
 * @author 新云鸟
 */
public interface DeptService {

    /**
     * 根据id获取部门信息
     *
     * @param id 部门id
     * @return DeptVO 部门信息
     */
    DeptVO get(Long id);

    /**
     * 批量获取部门信息
     *
     * @param ids 部门id集合
     * @return List<DeptVO> 部门信息列表
     */
    List<DeptVO> batchGet(Set<Long> ids);

    /**
     * 创建部门
     *
     * @param req 部门创建请求
     * @return DeptVO 部门信息
     */
    DeptVO create(DeptCreateReq req);

    /**
     * 更新部门
     *
     * @param id  部门id
     * @param req 部门更新请求
     * @return DeptVO 部门信息
     */
    DeptVO update(Long id, DeptUpdateReq req);

    /**
     * 删除部门
     *
     * @param id 部门id
     */
    void delete(Long id);

    /**
     * 全量查询部门
     *
     * @param req 查询请求
     * @return List<DeptVO> 部门信息列表
     */
    List<DeptVO> list(DeptQueryReq req);

    /**
     * 分页查询部门
     *
     * @param req         查询请求
     * @param current     页码
     * @param pageSize    页大小
     * @param searchCount 是否查询总数
     * @return PageResult<DeptVO> 部门分页信息
     */
    PageResult<DeptVO> page(DeptQueryReq req, Long current, Long pageSize, boolean searchCount);

    /**
     * 获取完整部门树
     *
     * @return DeptTreeVO 部门树
     */
    List<DeptTreeVO> getDeptTree();

    /**
     * 获取指定部门的子部门树
     *
     * @param id 部门id
     * @return DeptTreeVO 部门树
     */
    DeptTreeVO getDeptTree(Long id);
}
