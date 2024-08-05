package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.dto.UserLoginDto;
import com.luckybird.springbackend.dto.UserRegistrationDto;
import com.luckybird.springbackend.entity.User;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.vo.UserLoginVo;
import com.luckybird.springbackend.vo.UserRegistrationVo;
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

    public UserRegistrationVo convertToUserRegistrationVo(User user) {
        UserRegistrationVo userRegistrationVo = new UserRegistrationVo();
        userRegistrationVo.setId(user.getId());
        userRegistrationVo.setUsername(user.getUsername());
        return userRegistrationVo;
    }

    public UserLoginVo convertToUserLoginVo(User user) {
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setId(user.getId());
        userLoginVo.setUsername(user.getUsername());
        return userLoginVo;
    }

    @PostMapping("/register")
    public UserRegistrationVo register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User user = userService.register(userRegistrationDto);
        return convertToUserRegistrationVo(user);
    }

    @PostMapping("/login")
    public UserLoginVo login(@RequestBody @Valid UserLoginDto userLoginDto) {
        User user = userService.login(userLoginDto);
        return convertToUserLoginVo(user);
    }
}
