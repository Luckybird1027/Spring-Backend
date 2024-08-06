package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.dto.KeywordSearchDto;
import com.luckybird.springbackend.dto.UpdateUsernameDto;
import com.luckybird.springbackend.dto.UserLoginDto;
import com.luckybird.springbackend.dto.UserRegistrationDto;
import com.luckybird.springbackend.entity.User;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.vo.UserLoginVo;
import com.luckybird.springbackend.vo.UserRegistrationVo;
import com.luckybird.springbackend.vo.UserSearchVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 注册用户
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserRegistrationVo register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User user = userService.register(userRegistrationDto);
        return convertToUserRegistrationVo(user);
    }

    /**
     * 登录用户
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginVo login(@RequestBody @Valid UserLoginDto userLoginDto) {
        User user = userService.login(userLoginDto);
        return convertToUserLoginVo(user);
    }

    /**
     * CRUD操作
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationVo createUser(@RequestBody @Valid UserRegistrationDto dto) {
        User user = userService.save(dto);
        return convertToUserRegistrationVo(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRegistrationVo updateUser(@PathVariable Long id, @RequestBody @Valid UserRegistrationDto dto) {
        User user = userService.update(id, dto);
        return convertToUserRegistrationVo(user);
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public UserRegistrationVo updateUsername(@PathVariable Long id, @RequestBody UpdateUsernameDto dto) {
        User user = userService.updateUsername(id, dto.getUsername());
        return convertToUserRegistrationVo(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRegistrationVo getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return convertToUserRegistrationVo(user);
    }

    /**
     * 关键字搜索用户
     */
    @PostMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public UserSearchVo searchUser(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody KeywordSearchDto dto) {
        List<User> users = userService.findByUsername(dto.getKeyword(), current, rows);
        return new UserSearchVo(users, searchCount);
    }
}
