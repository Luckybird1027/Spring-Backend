package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.req.*;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.api.vo.UserVO;

import java.util.List;
import java.util.Set;

/**
 * 用户服务接口
 *
 * @author 新云鸟
 */
public interface UserService {

    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return UserVO 用户信息
     */
    UserVO get(Long id);

    /**
     * 批量获取用户信息
     * @param ids 用户id集合
     * @return List<UserVO> 用户信息列表
     */
    List<UserVO> batchGet(Set<Long> ids);

    /**
     * 创建用户
     * @param req 用户创建请求
     * @return UserVO 用户信息
     */
    UserVO create(UserCreateReq req);

    /**
     * 更新用户
     * @param id 用户id
     * @param req 用户更新请求
     * @return UserVO 用户信息
     */
    UserVO update(Long id, UserUpdateReq req);

    /**
     * 删除用户
     * @param id 用户id
     */
    void delete(Long id);

    /**
     * 全量查询用户
     * @param req 查询请求
     * @return List<UserVO> 用户信息列表
     */
    List<UserVO> list(UserQueryReq req);

    /**
     * 分页查询用户
     * @param req 查询请求
     * @param current 页码
     * @param pageSize 页大小
     * @param searchCount 是否查询总数
     * @return PageResult<UserVO> 用户分页信息
     */
    PageResult<UserVO> page(UserQueryReq req, int current, int pageSize, boolean searchCount);

    /**
     * 用户登录
     * @param req 用户登录请求
     * @return UserVO 用户信息
     */
    UserVO login(UserLoginReq req);

    /**
     * 用户修改密码
     * @param id 用户id
     * @param req 用户修改密码请求
     */
    void changePassword(Long id,UserChangePasswordReq req);
}
