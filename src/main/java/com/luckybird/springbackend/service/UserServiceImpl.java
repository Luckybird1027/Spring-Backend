package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserLoginDto;
import com.luckybird.springbackend.dto.UserRegistrationDto;
import com.luckybird.springbackend.entity.User;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.reposity.UserRepository;
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

    public User convertToUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());
        return user;
    }

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
    public User login(UserLoginDto loginDto) {
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

    @Override
    public User save(UserRegistrationDto dto) {
        User user = convertToUser(dto);
        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            return user;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    // TODO: 更改密码操作应该单独实现一个接口
    @Override
    public User update(Long id, UserRegistrationDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUsername(Long id, String username) {
        User user = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        user.setUsername(username);
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public List<User> findByUsername(String username, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return userRepository.findByUsernameContaining(username, pageable);
    }
}
