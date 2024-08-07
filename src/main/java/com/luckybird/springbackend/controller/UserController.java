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
@RequestMapping("/v1/users")
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
    public UserVo updateUser(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return userService.update(id, dto);
    }

    @PutMapping("/{id}/username")
    public UserVo updateUsername(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUsername(id, userDto.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserVo getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * 关键字-用户名 搜索用户-分页查询
     */
    @PostMapping("/page")
    public UserSearchVo searchUser(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserSearchDto dto) {
        return userService.searchByUsername(dto, current, rows, searchCount);
    }

    /**
     * 关键字-用户名 搜索用户-全量查询
     */
    @PostMapping("/list")
    public List<User> searchAllUser(
            @RequestBody UserSearchDto dto) {
        return userService.searchByUsername(dto);
    }

    /**
     * BatchGet 批量查询
     */
    @PostMapping("/batchGet")
    public List<User> batchGetUser(@RequestBody List<Long> ids) {
        return userService.batchGetUsers(ids);
    }
}
