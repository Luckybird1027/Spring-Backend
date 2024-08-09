package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserDTO;
import com.luckybird.springbackend.dto.UserSearchDTO;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.vo.UserSearchVO;
import com.luckybird.springbackend.vo.UserVO;

import java.util.List;

/**
 * @author 新云鸟
 */
public interface UserService {

    UserVO register(UserDTO dto);

    UserVO login(UserDTO dto);

    UserVO save(UserDTO dto);

    UserVO update(Long id, UserDTO dto);

    UserVO updateByIdAndUsername(Long id, String username);

    void delete(Long id);

    UserVO getById(Long id);

    UserSearchVO listByUsername(UserSearchDTO dto, int currentPage, int pageSize, boolean searchCount);

    List<UserPO> listByUsername(UserSearchDTO dto);

    long countByUsername(String username);

    List<UserPO> batchGetUsers(List<Long> ids);
}
