package com.luckybird.backendapp.controller;

import com.luckybird.backendapp.dto.UserLoginDto;
import com.luckybird.backendapp.dto.UserRegistrationDto;
import com.luckybird.backendapp.entity.User;
import com.luckybird.backendapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 新云鸟
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return userService.register(userRegistrationDto);
    }

    @PostMapping("/login")
    public User login(@RequestBody @Valid UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }
}
