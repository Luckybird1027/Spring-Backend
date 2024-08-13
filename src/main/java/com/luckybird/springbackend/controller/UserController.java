package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.api.UserApi;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.api.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户管理控制器
 *
 * @author 新云鸟
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

//    /**
//     * 注册用户
//     */
//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.OK)
//    public UserVO register(@RequestBody @Valid UserCreateReq req) {
//        return userService.register(req);
//    }
//
//    /**
//     * 登录用户
//     */
//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public UserVO login(@RequestBody @Valid UserLoginReq dto) {
//        return userService.login(dto);
//    }

    /////////////////CURD/////////////////

    /**
     * Get 单个查询
     * @param id 用户id
     * @return UserVO 用户信息
     */
    @Override
    @GetMapping("/v1/users/{id}")
    public UserVO get(@PathVariable Long id) {
        return userService.get(id);
    }

    /**
     * BatchGet 批量查询
     * @param ids 用户id数组
     * @return List<UserVO> 用户信息列表
     */
    @Override
    @PostMapping("/v1/users/batchGet")
    public List<UserVO> batchGet(@RequestBody Set<Long> ids) {
        return userService.batchGet(ids);
    }

    /**
     * Create 创建
     * @param req 用户创建请求
     * @return UserVO 用户信息
     */
    @Override
    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVO create(@RequestBody @Valid UserCreateReq req) {
        return userService.create(req);
    }

    /**
     * Update 更新
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
     * Delete 删除
     * @param id 用户id
     */
    @Override
    @DeleteMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * List 用户名 全量查询
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
     * Page 用户名 分页查询
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
