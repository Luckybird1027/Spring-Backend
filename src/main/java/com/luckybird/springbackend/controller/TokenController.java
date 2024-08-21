package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.annotation.NoAuthRequired;
import com.luckybird.springbackend.api.req.UserChangePasswordReq;
import com.luckybird.springbackend.api.req.UserLoginReq;
import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.service.TokenService;
import com.luckybird.springbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @NoAuthRequired
    public TokenVO login(@RequestBody @Valid UserLoginReq req) {
        return userService.login(req);
    }

    /**
     * 登出用户
     *
     * @param rawToken 未处理token
     */
    @PostMapping("/v1/users/logout")
    public void logout(@RequestHeader("Authorization") String rawToken) {
        userService.logout(rawToken);
    }

    /**
     * 修改密码
     *
     * @param rawToken 未处理token
     * @param req 密码修改请求
     */
    @PostMapping("/v1/users/changePassword")
    public void changePassword(@RequestHeader("Authorization") String rawToken, @RequestBody @Valid UserChangePasswordReq req) {
        userService.changePassword(rawToken, req);
    }

    /**
     * 本接口暂时用于测试
     *
     * @param accessToken 验证token
     */
    @PostMapping("v1/token/testVerity")
    @NoAuthRequired
    public TokenVO verityToken(@RequestParam(name = "accessToken") String accessToken) {
        return tokenService.verifyToken(accessToken);
    }
}