package com.luckybird.user.controller;

import com.luckybird.auth.annotation.NoAuth;
import com.luckybird.auth.base.TokenInfo;
import com.luckybird.common.base.PageResult;
import com.luckybird.common.utils.ContextUtils;
import com.luckybird.user.api.UserApi;
import com.luckybird.user.api.req.UserChangePasswordReq;
import com.luckybird.user.api.req.UserCreateReq;
import com.luckybird.user.api.req.UserLoginReq;
import com.luckybird.user.api.req.UserQueryReq;
import com.luckybird.user.api.req.UserUpdateReq;
import com.luckybird.user.api.vo.UserVO;
import com.luckybird.user.service.UserService;
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
     *
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
     *
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
     *
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
     *
     * @param id  用户id
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
     *
     * @param id 用户id
     */
    @Override
    @DeleteMapping("/v1/users/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * 查询用户列表
     *
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
     *
     * @param current     当前页
     * @param rows        每页条数
     * @param searchCount 是否搜索总数
     * @param req         用户查询请求
     * @return PageResult<UserVO> 用户信息分页
     */
    @Override
    @PostMapping("/v1/users/page")
    public PageResult<UserVO> page(
            @RequestParam(name = "current", defaultValue = "1") Long current,
            @RequestParam(name = "rows", defaultValue = "10") Long rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserQueryReq req) {
        return userService.page(req, current, rows, searchCount);
    }

    /**
     * 登录用户
     *
     * @param req 用户登录请求
     * @return UserVO 用户信息
     */
    @PostMapping("/v1/users/login")
    @NoAuth
    public TokenInfo login(@RequestBody @Valid UserLoginReq req) {
        return userService.login(req);
    }

    /**
     * 登出用户
     */
    @PostMapping("/v1/users/logout")
    public void logout() {
        userService.logout(ContextUtils.getUserInfo().getId());
    }

    /**
     * 修改密码
     *
     * @param req 密码修改请求
     */
    @PostMapping("/v1/users/changePassword")
    public void changePassword(@RequestBody @Valid UserChangePasswordReq req) {
        userService.changePassword(ContextUtils.getUserInfo().getId(), req);
    }

}
