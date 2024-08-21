package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.api.UserApi;
import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 *
 * @author 新云鸟
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    /**
     * 获取用户
     * @param id 用户id
     * @return UserVO 用户信息
     */
    @Override
    @GetMapping("/v1/users/{id}")
    public UserVO get(@PathVariable Long id) {
        return userService.get(id);
    }

    /**
     * 批量获取用户
     * @param ids 用户id数组
     * @return List<UserVO> 用户信息列表
     */
    @Override
    @PostMapping("/v1/users/batchGet")
    public List<UserVO> batchGet(@RequestBody Set<Long> ids) {
        return userService.batchGet(ids);
    }

    /**
     * 创建用户
     * @param req 用户创建请求
     * @return UserVO 用户信息
     */
    @Override
    @PostMapping("/v1/users")
    public UserVO create(@RequestBody @Valid UserCreateReq req) {
        return userService.create(req);
    }

    /**
     * 编辑用户
     * @param id 用户id
     * @param req 用户更新请求
     * @return UserVO 用户信息
     */
    @Override
    @PutMapping("/v1/users/{id}")
    public UserVO update(@PathVariable Long id, @RequestBody @Valid UserUpdateReq req) {
        return userService.update(id, req);
    }

    /**
     * 删除用户
     * @param id 用户id
     */
    @Override
    @DeleteMapping("/v1/users/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * 查询用户列表
     * @param req 用户查询请求
     * @return List<UserVO> 用户信息列表
     */
    @Override
    @PostMapping("/v1/users/list")
    public List<UserVO> list(
            @RequestBody UserQueryReq req) {
        return userService.list(req);
    }

    /**
     * 查询用户分页
     * @param current 当前页
     * @param rows 每页条数
     * @param searchCount 是否搜索总数
     * @param req 用户查询请求
     * @return PageResult<UserVO> 用户信息分页
     */
    @Override
    @PostMapping("/v1/users/page")
    public PageResult<UserVO> page(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserQueryReq req) {
        return userService.page(req, current, rows, searchCount);
    }
}
