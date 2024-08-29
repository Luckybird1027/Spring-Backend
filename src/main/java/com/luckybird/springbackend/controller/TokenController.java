package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.api.req.UserChangePasswordReq;
import com.luckybird.springbackend.api.req.UserLoginReq;
import com.luckybird.springbackend.common.annotation.NoAuth;
import com.luckybird.springbackend.common.base.TokenInfo;
import com.luckybird.springbackend.common.util.ContextUtil;
import com.luckybird.springbackend.service.TokenService;
import com.luckybird.springbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token鉴权管理
 *
 * @author 新云鸟
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final UserService userService;

    // TODO: Token鉴权管理待编写

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
        userService.logout(ContextUtil.getUserInfo().getId());
    }

    /**
     * 修改密码
     *
     * @param req 密码修改请求
     */
    @PostMapping("/v1/users/changePassword")
    public void changePassword(@RequestBody @Valid UserChangePasswordReq req) {
        userService.changePassword(ContextUtil.getUserInfo().getId(), req);
    }

}