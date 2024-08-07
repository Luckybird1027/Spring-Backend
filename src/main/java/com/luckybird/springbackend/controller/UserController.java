package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.dto.UserDto;
import com.luckybird.springbackend.dto.UserSearchDto;
import com.luckybird.springbackend.entity.User;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.vo.UserSearchVo;
import com.luckybird.springbackend.vo.UserVo;
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

    public UserVo convertToUserVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        return userVo;
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserVo register(@RequestBody @Valid UserDto userDto) {
        User user = userService.register(userDto);
        return convertToUserVo(user);
    }

    /**
     * 登录用户
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserVo login(@RequestBody @Valid UserDto userDto) {
        User user = userService.login(userDto);
        return convertToUserVo(user);
    }

    /**
     * CRUD操作
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVo createUser(@RequestBody @Valid UserDto dto) {
        User user = userService.save(dto);
        return convertToUserVo(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserVo updateUser(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        User user = userService.update(id, dto);
        return convertToUserVo(user);
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public UserVo updateUsername(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.updateUsername(id, userDto.getUsername());
        return convertToUserVo(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserVo getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return convertToUserVo(user);
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
            @RequestBody UserSearchDto dto) {
        List<User> users = userService.findByUsername(dto.getKeyword(), current, rows);
        if (searchCount) {
            Integer count = userService.countByUsername(dto.getKeyword());
            return new UserSearchVo(users, count);
        }
        return new UserSearchVo(users);
    }
}
