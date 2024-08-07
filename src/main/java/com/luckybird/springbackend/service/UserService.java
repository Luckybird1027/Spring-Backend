package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserDto;
import com.luckybird.springbackend.entity.User;

import java.util.List;

/**
 * @author 新云鸟
 */
public interface UserService {

    User register(UserDto registrationDto);

    User login(UserDto userDto);

    User save(UserDto dto);

    User update(Long id, UserDto dto);

    User updateUsername(Long id, String username);

    void delete(Long id);

    User findById(Long id);

    List<User> findByUsername(String username, int currentPage, int pageSize);

    Integer countByUsername(String username);
}
