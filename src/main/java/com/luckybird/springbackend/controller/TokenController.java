package com.luckybird.springbackend.controller;

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
    public TokenVO login(@RequestBody @Valid UserLoginReq req) {
        return userService.login(req);
    }

    /**
     * 修改密码
     *
     * @param id  用户id
     * @param req 密码修改请求
     */
    @PostMapping("/v1/users/changePassword/{id}")
    public void changePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordReq req) {
        userService.changePassword(id, req);
    }

    /**
     * 本接口暂时用于测试
     *
     * @param accessToken 验证token
     */
    @PostMapping("v1/token/testVerity")
    public TokenVO verityToken(@RequestParam(name = "accessToken") String accessToken) {
        return tokenService.verifyToken(accessToken);
    }
}

