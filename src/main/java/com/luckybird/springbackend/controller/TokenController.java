package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.service.TokenService;
import lombok.RequiredArgsConstructor;
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

    // TODO: Token鉴权管理待编写
}
