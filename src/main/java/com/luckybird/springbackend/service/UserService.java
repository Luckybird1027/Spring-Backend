package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserDto;
import com.luckybird.springbackend.dto.UserSearchDto;
import com.luckybird.springbackend.entity.User;
import com.luckybird.springbackend.vo.UserSearchVo;
import com.luckybird.springbackend.vo.UserVo;

import java.util.List;

/**
 * @author 新云鸟
 */
public interface UserService {

    UserVo register(UserDto registrationDto);

    UserVo login(UserDto userDto);

    UserVo save(UserDto dto);

    UserVo update(Long id, UserDto dto);

    UserVo updateUsername(Long id, String username);

    void delete(Long id);

    UserVo findById(Long id);

    UserSearchVo searchByUsername(UserSearchDto userSearchDto, int currentPage, int pageSize, boolean searchCount);

    List<User> searchByUsername(UserSearchDto userSearchDto);

    long countByUsername(String username);

    List<User> batchGetUsers(List<Long> ids);
}
