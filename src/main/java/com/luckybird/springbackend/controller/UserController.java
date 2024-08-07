package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.dto.UserDto;
import com.luckybird.springbackend.dto.UserSearchDto;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.vo.UserSearchVo;
import com.luckybird.springbackend.vo.UserVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author 新云鸟
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 注册用户
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserVo register(@RequestBody @Valid UserDto userDto) {
        return userService.register(userDto);
    }

    /**
     * 登录用户
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserVo login(@RequestBody @Valid UserDto userDto) {
        return userService.login(userDto);
    }

    /**
     * CRUD操作
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVo createUser(@RequestBody @Valid UserDto dto) {
        return userService.save(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserVo updateUser(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return userService.update(id, dto);
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public UserVo updateUsername(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUsername(id, userDto.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserVo getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * 关键字-用户名 搜索用户
     */
    @PostMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public UserSearchVo searchUser(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserSearchDto dto) {
        return userService.searchByUsername(dto, current, rows, searchCount);
    }
}
