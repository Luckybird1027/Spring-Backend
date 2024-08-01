package com.luckybird.backendapp.service;

import com.luckybird.backendapp.dto.UserRegistrationDto;
import com.luckybird.backendapp.entity.User;
import com.luckybird.backendapp.exception.DatabaseErrorException;
import com.luckybird.backendapp.exception.UsernameAlreadyExistsException;
import com.luckybird.backendapp.reposity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 新云鸟
 */
@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User register(UserRegistrationDto registrationDto) {
        // 检查用户是否存在
        Optional<User> existingUser = userRepository.findByusername(registrationDto.getUsername());
        if (existingUser.isPresent()) {
            System.out.println("User " + registrationDto.getUsername() + " already exists");
            throw new UsernameAlreadyExistsException();
        }
        // 检查完成，注册用户
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        user.setPassword(encodedPassword);
        try {
            userRepository.save(user);
            System.out.println("User " + user.getUsername() + " registered successfully");
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseErrorException();
        }
    }
}
