package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserLoginDto;
import com.luckybird.springbackend.dto.UserRegistrationDto;
import com.luckybird.springbackend.entity.User;

/**
 * @author 新云鸟
 */
public interface UserService {

    User register(UserRegistrationDto registrationDto);

    User login(UserLoginDto loginDto);
}
