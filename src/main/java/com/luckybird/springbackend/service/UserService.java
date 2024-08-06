package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserLoginDto;
import com.luckybird.springbackend.dto.UserRegistrationDto;
import com.luckybird.springbackend.entity.User;

import java.util.List;

/**
 * @author 新云鸟
 */
public interface UserService {

    User register(UserRegistrationDto registrationDto);

    User login(UserLoginDto loginDto);

    User save(UserRegistrationDto dto);

    User update(Long id, UserRegistrationDto dto);

    User updateUsername(Long id, String username);

    void delete(Long id);

    User findById(Long id);

    List<User> findByUsername(String username, int currentPage, int pageSize);
}
