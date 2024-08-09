package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.dto.UserDTO;
import com.luckybird.springbackend.dto.UserSearchDTO;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.vo.UserSearchVO;
import com.luckybird.springbackend.vo.UserVO;
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
    public UserVO register(@RequestBody @Valid UserDTO dto) {
        return userService.register(dto);
    }

    /**
     * 登录用户
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserVO login(@RequestBody @Valid UserDTO dto) {
        return userService.login(dto);
    }

    /**
     * CRUD操作
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVO createUser(@RequestBody @Valid UserDTO dto) {
        return userService.save(dto);
    }

    @PutMapping("/{id}")
    public UserVO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO dto) {
        return userService.update(id, dto);
    }

    @PutMapping("/{id}/username")
    public UserVO updateUserByUsername(@PathVariable Long id, @RequestBody UserDTO dto) {
        return userService.updateByIdAndUsername(id, dto.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserVO getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * 关键字-用户名 搜索用户-分页查询
     */
    @PostMapping("/page")
    public UserSearchVO listUsers(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserSearchDTO dto) {
        return userService.listByUsername(dto, current, rows, searchCount);
    }

    /**
     * 关键字-用户名 搜索用户-全量查询
     */
    @PostMapping("/list")
    public List<UserPO> listUsers(
            @RequestBody UserSearchDTO dto) {
        return userService.listByUsername(dto);
    }

    /**
     * BatchGet 批量查询
     */
    @PostMapping("/batchGet")
    public List<UserPO> listUsers(@RequestBody List<Long> ids) {
        return userService.batchGetUsers(ids);
    }
}
