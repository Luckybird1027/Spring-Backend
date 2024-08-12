package com.luckybird.springbackend.api;

import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.base.PageResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * @author 新云鸟
 */
//Spring Cloud OpenFeign 注解
//@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserApi {

    /**
     * Get 单个查询
     * @param id 用户id
     * @return UserVO 用户信息
     */
    @GetMapping("/v1/users/{id}")
    UserVO get(@PathVariable Long id);

    /**
     * BatchGet 批量查询
     * @param ids 用户id数组
     * @return List<UserVO> 用户信息列表
     */
    @PostMapping("/v1/users/batchGet")
    List<UserVO> batchGet(@RequestBody Set<Long> ids);

    /**
     * Create 创建
     * @param req 用户创建请求
     * @return UserVO 用户信息
     */
    @PostMapping("/v1/users")
    UserVO create(@RequestBody @Valid UserCreateReq req);

    /**
     * Update 更新
     * @param id 用户id
     * @param req 用户更新请求
     * @return UserVO 用户信息
     */
    @PutMapping("/v1/users/{id}")
    UserVO update(@PathVariable Long id, @RequestBody @Valid UserUpdateReq req);

    /**
     * Delete 删除
     * @param id 用户id
     */
    @DeleteMapping("/v1/users/{id}")
    void delete(@PathVariable Long id);

    /**
     * List 用户名 全量查询
     * @param req 用户查询请求
     * @return List<UserVO> 用户信息列表
     */
    @PostMapping("/v1/users/list")
    List<UserVO> list(
            @RequestBody UserQueryReq req);

    /**
     * Page 用户名 分页查询
     * @param current 当前页
     * @param rows 每页条数
     * @param searchCount 是否搜索总数
     * @param req 用户查询请求
     * @return PageResult<UserVO> 用户信息分页
     */
    @PostMapping("/v1/users/page")
    PageResult<UserVO> page(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserQueryReq req);
}
