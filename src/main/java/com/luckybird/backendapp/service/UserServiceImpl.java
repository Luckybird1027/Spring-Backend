package com.luckybird.backendapp.service;

import com.luckybird.backendapp.dto.UserLoginDto;
import com.luckybird.backendapp.dto.UserRegistrationDto;
import com.luckybird.backendapp.entity.User;
import com.luckybird.backendapp.exception.*;
import com.luckybird.backendapp.reposity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 新云鸟
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public User register(UserRegistrationDto registrationDto) {
        // 检查用户是否存在
        Optional<User> existingUser = userRepository.findByUsername(registrationDto.getUsername());
        if (existingUser.isPresent()) {
            throw new BizException(ExceptionMessages.USERNAME_ALREADY_EXISTS);
        }
        // 检查完成，注册用户
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("User " + user.getUsername() + " registered successfully");
        return user;
    }

    @Override
    public User login(UserLoginDto loginDto){
        //检查用户是否存在
        Optional<User> existingUser = userRepository.findByUsername(loginDto.getUsername());
        if (existingUser.isEmpty()) {
            throw new BizException(ExceptionMessages.USER_NOT_EXIST);
        }
        // 检查密码是否正确
        User user = existingUser.get();
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionMessages.INCORRECT_PASSWORD);
        }
        // 登录成功
        log.info("User " + loginDto.getUsername() + " logged in successfully");
        return user;
    }
}
