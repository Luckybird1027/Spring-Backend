package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserDto;
import com.luckybird.springbackend.dto.UserSearchDto;
import com.luckybird.springbackend.entity.User;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.reposity.UserRepository;
import com.luckybird.springbackend.vo.UserSearchVo;
import com.luckybird.springbackend.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public User convertToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserVo convertToUserVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        return userVo;
    }

    @Override
    public UserVo register(UserDto userDto) {
        // 检查用户是否存在
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new BizException(ExceptionMessages.USERNAME_ALREADY_EXISTS);
        }
        // 检查完成，注册用户
        User user = new User();
        user.setUsername(userDto.getUsername());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("User " + user.getUsername() + " registered successfully");
        return convertToUserVo(user);
    }

    @Override
    public UserVo login(UserDto userDto) {
        //检查用户是否存在
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isEmpty()) {
            throw new BizException(ExceptionMessages.USER_NOT_EXIST);
        }
        // 检查密码是否正确
        User user = existingUser.get();
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionMessages.INCORRECT_PASSWORD);
        }
        // 登录成功
        log.info("User " + userDto.getUsername() + " logged in successfully");
        return convertToUserVo(user);
    }

    @Override
    public UserVo save(UserDto userDto) {
        User user = convertToUser(userDto);
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            return convertToUserVo(existingUser.get());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return convertToUserVo(user);
    }

    // TODO: 更改密码操作应该单独实现一个接口
    // TODO: update操作只修改userDto中不为空的的属性，其他属性不修改
    @Override
    public UserVo update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return convertToUserVo(user);
    }

    @Override
    public UserVo updateUsername(Long id, String username) {
        User user = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        user.setUsername(username);
        userRepository.save(user);
        return convertToUserVo(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserVo findById(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        return convertToUserVo(user);
    }

    @Override
    public UserSearchVo searchByUsername(UserSearchDto userSearchDto, int currentPage, int pageSize, boolean searchCount) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        List<User> users = userRepository.findByUsernameContaining(userSearchDto.getKeyword(), pageable);
        if (searchCount) {
            long count = countByUsername(userSearchDto.getKeyword());
            return new UserSearchVo(users, count);
        }
        return new UserSearchVo(users);
    }

    @Override
    public long countByUsername(String username) {
        return userRepository.countByUsernameContaining(username);
    }


}
