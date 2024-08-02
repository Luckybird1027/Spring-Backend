package com.luckybird.backendapp.service;

import com.luckybird.backendapp.dto.UserLoginDto;
import com.luckybird.backendapp.dto.UserRegistrationDto;
import com.luckybird.backendapp.entity.User;

/**
 * @author 新云鸟
 */
public interface UserService {

    User register(UserRegistrationDto registrationDto);

    User login(UserLoginDto loginDto);
}
