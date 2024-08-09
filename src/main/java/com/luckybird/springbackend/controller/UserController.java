package com.luckybird.springbackend.controller;

import com.luckybird.springbackend.api.UserApi;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.service.UserService;
import com.luckybird.springbackend.api.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author 新云鸟
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

//    /**
//     * 注册用户
//     */
//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.OK)
//    public UserVO register(@RequestBody @Valid UserCreateReq req) {
//        return userService.register(req);
//    }
//
//    /**
//     * 登录用户
//     */
//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public UserVO login(@RequestBody @Valid UserLoginReq dto) {
//        return userService.login(dto);
//    }

    /////////////////CURD///////////

    @Override
    @GetMapping("/v1/users/{id}")
    public UserVO get(@PathVariable Long id) {
        return userService.get(id);
    }

    /**
     * BatchGet 批量查询
     */
    @Override
    @PostMapping("/v1/users/batchGet")
    public List<UserVO> batchGet(@RequestBody Set<Long> ids) {
        return userService.batchGet(ids);
    }

    /**
     * CRUD操作
     */
    @Override
    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVO create(@RequestBody @Valid UserCreateReq dto) {
        return userService.create(dto);
    }

    @Override
    @PutMapping("/v1/users/{id}")
    public UserVO update(@PathVariable Long id, @RequestBody @Valid UserUpdateReq dto) {
        return userService.update(id, dto);
    }

    @Override
    @DeleteMapping("/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * 关键字-用户名 搜索用户-全量查询
     */
    @Override
    @PostMapping("/v1/users/list")
    public List<UserVO> list(
            @RequestBody UserQueryReq dto) {
        return userService.list(dto);
    }

    /**
     * 关键字-用户名 搜索用户-分页查询
     */
    @Override
    @PostMapping("/v1/users/page")
    public PageResult<UserVO> page(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserQueryReq dto) {
        return userService.page(dto, current, rows, searchCount);
    }
}
