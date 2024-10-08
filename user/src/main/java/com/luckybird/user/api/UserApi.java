package com.luckybird.user.api;

import com.luckybird.common.base.PageResult;
import com.luckybird.user.api.req.UserCreateReq;
import com.luckybird.user.api.req.UserQueryReq;
import com.luckybird.user.api.req.UserUpdateReq;
import com.luckybird.user.api.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;


/**
 * 用户管理API
 *
 * @author 新云鸟
 */
//Spring Cloud OpenFeign 注解
//@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserApi {

    /**
     * 获取用户
     *
     * @param id 用户id
     * @return UserVO 用户信息
     */
    @GetMapping("/v1/users/{id}")
    UserVO get(@PathVariable Long id);

    /**
     * 批量获取用户
     *
     * @param ids 用户id集合
     * @return List<UserVO> 用户信息列表
     */
    @PostMapping("/v1/users/batchGet")
    List<UserVO> batchGet(@RequestBody Set<Long> ids);

    /**
     * 创建用户
     *
     * @param req 用户创建请求
     * @return UserVO 用户信息
     */
    @PostMapping("/v1/users")
    UserVO create(@RequestBody @Valid UserCreateReq req);

    /**
     * 编辑用户
     *
     * @param id  用户id
     * @param req 用户更新请求
     * @return UserVO 用户信息
     */
    @PutMapping("/v1/users/{id}")
    UserVO update(@PathVariable Long id, @RequestBody @Valid UserUpdateReq req);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @DeleteMapping("/v1/users/{id}")
    void delete(@PathVariable Long id);

    /**
     * 查询用户列表
     *
     * @param req 用户查询请求
     * @return List<UserVO> 用户信息列表
     */
    @PostMapping("/v1/users/list")
    List<UserVO> list(
            @RequestBody UserQueryReq req);

    /**
     * 查询用户分页
     *
     * @param current     当前页
     * @param rows        每页条数
     * @param searchCount 是否搜索总数
     * @param req         用户查询请求
     * @return PageResult<UserVO> 用户信息分页
     */
    @PostMapping("/v1/users/page")
    PageResult<UserVO> page(
            @RequestParam(name = "current", defaultValue = "1") Long current,
            @RequestParam(name = "rows", defaultValue = "10") Long rows,
            @RequestParam(name = "searchCount", defaultValue = "false") boolean searchCount,
            @RequestBody UserQueryReq req);

}
