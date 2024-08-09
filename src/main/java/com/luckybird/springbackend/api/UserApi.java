package com.luckybird.springbackend.api;

import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.base.PageResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * @author 新云鸟
 */
//Spring Cloud OpenFeign 注解
//@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserApi {

    @GetMapping("/v1/users/{id}")
    UserVO get(@PathVariable Long id);

    @PostMapping("/v1/users/batchGet")
    List<UserVO> batchGet(@RequestBody Set<Long> ids);

    @PostMapping("/v1/users")
    UserVO create(@RequestBody @Valid UserCreateReq dto);

    @PutMapping("/v1/users/{id}")
    UserVO update(@PathVariable Long id, @RequestBody @Valid UserUpdateReq dto);

    @DeleteMapping("/v1/users/{id}")
    void delete(@PathVariable Long id);

    @PostMapping("/v1/users/list")
    List<UserVO> list(
            @RequestBody UserQueryReq dto);

    @PostMapping("/v1/users/page")
    PageResult<UserVO> page(
            @RequestParam(name = "current", defaultValue = "1") int current,
            @RequestParam(name = "rows", defaultValue = "10") int rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserQueryReq dto);
}
