package com.luckybird.dept.api;

import com.luckybird.common.base.PageResult;
import com.luckybird.dept.api.req.DeptCreateReq;
import com.luckybird.dept.api.req.DeptMoveReq;
import com.luckybird.dept.api.req.DeptQueryReq;
import com.luckybird.dept.api.req.DeptUpdateReq;
import com.luckybird.dept.api.vo.DeptTreeVO;
import com.luckybird.dept.api.vo.DeptVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * 部门管理API
 *
 * @author 新云鸟
 */
//Spring Cloud OpenFeign 注解
//@FeignClient(name = "dept-service", url = "${dept-service.url}")
public interface DeptApi {

    /**
     * 获取部门
     *
     * @param id 部门id
     * @return DeptVO 部门信息
     */
    @GetMapping("/v1/dept/{id}")
    DeptVO get(@PathVariable Long id);

    /**
     * 批量获取部门
     *
     * @param ids 部门id数组
     * @return List<DeptVO> 部门信息列表
     */
    @PostMapping("/v1/dept/batchGet")
    List<DeptVO> batchGet(@RequestBody Set<Long> ids);

    /**
     * 创建部门
     *
     * @param req 部门创建请求
     * @return DeptVO 部门信息
     */
    @PostMapping("/v1/dept")
    DeptVO create(@RequestBody @Valid DeptCreateReq req);

    /**
     * 编辑部门
     *
     * @param id  部门id
     * @param req 部门更新请求
     * @return DeptVO 部门信息
     */
    @PutMapping("/v1/dept/{id}")
    DeptVO update(@PathVariable Long id, @RequestBody @Valid DeptUpdateReq req);

    /**
     * 删除部门
     *
     * @param id 部门id
     */
    @DeleteMapping("/v1/dept/{id}")
    void delete(@PathVariable Long id);

    /**
     * 查询部门列表
     *
     * @param req 部门查询请求
     * @return List<DeptVO> 部门信息列表
     */
    @PostMapping("/v1/dept/list")
    List<DeptVO> list(@RequestBody DeptQueryReq req);

    /**
     * 查询部门分页
     *
     * @param current     当前页
     * @param rows        每页条数
     * @param searchCount 是否搜索总数
     * @param req         部门查询请求
     * @return PageResult<DeptVO> 部门信息分页
     */
    @PostMapping("/v1/dept/page")
    PageResult<DeptVO> page(
            @RequestParam(name = "current", defaultValue = "1") Long current,
            @RequestParam(name = "rows", defaultValue = "10") Long rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody DeptQueryReq req);

    /**
     * 获取完整部门树
     *
     * @return List<DeptTreeVO> 部门树
     */
    @GetMapping("/v1/dept/tree")
    List<DeptTreeVO> getTree();

    /**
     * 获取指定部门的子部门树
     *
     * @param id 部门id
     * @return DeptTreeVO 部门树
     */
    @GetMapping("/v1/dept/tree/{id}")
    DeptTreeVO getTree(@PathVariable Long id);

    /**
     * 移动部门
     *
     * @param id  部门id
     * @param req 部门移动请求
     * @return DeptTreeVO 移动后的部门树
     */
    @PostMapping("/v1/dept/tree/{id}")
    DeptTreeVO moveTree(@PathVariable Long id, @RequestBody DeptMoveReq req) throws ExecutionException, InterruptedException;
}
