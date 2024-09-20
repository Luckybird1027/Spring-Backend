package com.luckybird.dept.controller;

import com.luckybird.common.base.PageResult;
import com.luckybird.dept.api.DeptApi;
import com.luckybird.dept.api.req.DeptCreateReq;
import com.luckybird.dept.api.req.DeptMoveReq;
import com.luckybird.dept.api.req.DeptQueryReq;
import com.luckybird.dept.api.req.DeptUpdateReq;
import com.luckybird.dept.api.vo.DeptTreeVO;
import com.luckybird.dept.api.vo.DeptVO;
import com.luckybird.dept.service.DeptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 部门管理
 *
 * @author 新云鸟
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class DeptController implements DeptApi {

    private final DeptService deptService;

    /**
     * 获取部门
     *
     * @param id 部门id
     * @return DeptVO 部门信息
     */
    @Override
    @GetMapping("/v1/dept/{id}")
    public DeptVO get(@PathVariable Long id) {
        return deptService.get(id);
    }

    /**
     * 批量获取部门
     *
     * @param ids 部门id数组
     * @return List<DeptVO> 部门信息列表
     */
    @Override
    @PostMapping("/v1/dept/batchGet")
    public List<DeptVO> batchGet(@RequestBody Set<Long> ids) {
        return deptService.batchGet(ids);
    }

    /**
     * 创建部门
     *
     * @param req 部门创建请求
     * @return DeptVO 部门信息
     */
    @Override
    @PostMapping("/v1/dept")
    public DeptVO create(@RequestBody @Valid DeptCreateReq req) {
        return deptService.create(req);
    }

    /**
     * 编辑部门
     *
     * @param id  部门id
     * @param req 部门更新请求
     * @return DeptVO 部门信息
     */
    @Override
    @PutMapping("/v1/dept/{id}")
    public DeptVO update(@PathVariable Long id, @RequestBody @Valid DeptUpdateReq req) {
        return deptService.update(id, req);
    }

    /**
     * 删除部门
     *
     * @param id 部门id
     */
    @Override
    @DeleteMapping("/v1/dept/{id}")
    public void delete(@PathVariable Long id) {
        deptService.delete(id);
    }

    /**
     * 查询部门列表
     *
     * @param req 部门查询请求
     * @return List<DeptVO> 部门信息列表
     */
    @Override
    @PostMapping("/v1/dept/list")
    public List<DeptVO> list(@RequestBody DeptQueryReq req) {
        return deptService.list(req);
    }

    /**
     * 查询部门分页
     *
     * @param current     当前页
     * @param rows        每页条数
     * @param searchCount 是否搜索总数
     * @param req         部门查询请求
     * @return PageResult<DeptVO> 部门信息分页
     */
    @Override
    @PostMapping("/v1/dept/page")
    public PageResult<DeptVO> page(
            @RequestParam(name = "current", defaultValue = "1") Long current,
            @RequestParam(name = "rows", defaultValue = "10") Long rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody DeptQueryReq req) {
        return deptService.page(req, current, rows, searchCount);
    }

    /**
     * 获取完整部门树
     *
     * @return List<DeptTreeVO> 部门树
     */
    @Override
    @GetMapping("/v1/dept/tree")
    public List<DeptTreeVO> getTree() {
        return deptService.getDeptTree();
    }

    /**
     * 获取指定部门的子部门树
     *
     * @param id 部门id
     * @return DeptTreeVO 部门树
     */
    @Override
    @GetMapping("/v1/dept/tree/{id}")
    public DeptTreeVO getTree(@PathVariable Long id) {
        return deptService.getDeptTree(id);
    }

    /**
     * 移动部门
     *
     * @param id  部门id
     * @param req 部门移动请求
     * @return DeptTreeVO 移动后的部门树
     */
    @Override
    @PostMapping("/v1/dept/tree/{id}")
    public DeptTreeVO moveTree(@PathVariable Long id, @RequestBody DeptMoveReq req) {
        return deptService.moveDept(id, req);
    }
}
