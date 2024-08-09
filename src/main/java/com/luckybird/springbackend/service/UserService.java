package com.luckybird.springbackend.service;

import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.api.vo.UserVO;

import java.util.List;
import java.util.Set;

/**
 * @author 新云鸟
 */
public interface UserService {

//    UserVO register(UserCreateReq req);
//
//    UserVO login(UserLoginReq dto);

    UserVO get(Long id);

    List<UserVO> batchGet(Set<Long> ids);

    UserVO create(UserCreateReq dto);

    UserVO update(Long id, UserUpdateReq dto);

    void delete(Long id);

    List<UserVO> list(UserQueryReq req);

    PageResult<UserVO> page(UserQueryReq req, int current, int pageSize, boolean searchCount);
}
