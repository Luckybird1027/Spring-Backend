package com.luckybird.springbackend.service;

import com.luckybird.springbackend.dto.UserDTO;
import com.luckybird.springbackend.dto.UserSearchDTO;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.reposity.UserRepository;
import com.luckybird.springbackend.vo.UserSearchVO;
import com.luckybird.springbackend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 新云鸟
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserPO convertToUserPO(UserDTO dto) {
        UserPO po = new UserPO();
        po.setUsername(dto.getUsername());
        po.setPassword(dto.getPassword());
        return po;
    }

    public UserVO convertToUserVO(UserPO po) {
        UserVO vo = new UserVO();
        vo.setId(po.getId());
        vo.setUsername(po.getUsername());
        return vo;
    }

    @Override
    public UserVO register(UserDTO dto) {
        // 检查用户是否存在
        Optional<UserPO> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new BizException(ExceptionMessages.USERNAME_ALREADY_EXISTS);
        }
        // 检查完成，注册用户
        UserPO po = convertToUserPO(dto);
        po.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(po);
        log.info("User " + po.getUsername() + " registered successfully");
        return convertToUserVO(po);
    }

    @Override
    public UserVO login(UserDTO dto) {
        //检查用户是否存在
        Optional<UserPO> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isEmpty()) {
            throw new BizException(ExceptionMessages.USER_NOT_EXIST);
        }
        // 检查密码是否正确
        UserPO po = existingUser.get();
        if (!passwordEncoder.matches(dto.getPassword(), po.getPassword())) {
            throw new BizException(ExceptionMessages.INCORRECT_PASSWORD);
        }
        // 登录成功
        log.info("User " + dto.getUsername() + " logged in successfully");
        return convertToUserVO(po);
    }

    @Override
    public UserVO save(UserDTO dto) {
        UserPO po = convertToUserPO(dto);
        Optional<UserPO> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            return convertToUserVO(existingUser.get());
        }
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        userRepository.save(po);
        return convertToUserVO(po);
    }

    // TODO: 更改密码操作应该单独实现一个接口
    // TODO: update操作只修改userDto中不为空的的属性，其他属性不修改
    @Override
    public UserVO update(Long id, UserDTO dto) {
        UserPO po = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        po.setUsername(dto.getUsername());
        po.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(po);
        return convertToUserVO(po);
    }

    @Override
    public UserVO updateByIdAndUsername(Long id, String username) {
        UserPO po = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        po.setUsername(username);
        userRepository.save(po);
        return convertToUserVO(po);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserVO getById(Long id) {
        UserPO po = userRepository.findById(id).orElse(new UserPO());
        return convertToUserVO(po);
    }

    @Override
    public UserSearchVO listByUsername(UserSearchDTO dto, int currentPage, int pageSize, boolean searchCount) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        List<UserPO> po = userRepository.findByUsernameContaining(dto.getKeyword(), pageable);
        if (searchCount) {
            long count = countByUsername(dto.getKeyword());
            return new UserSearchVO(po, count);
        }
        return new UserSearchVO(po);
    }

    @Override
    public List<UserPO> listByUsername(UserSearchDTO dto) {
        return userRepository.findByUsernameContaining(dto.getKeyword());
    }

    @Override
    public long countByUsername(String username) {
        return userRepository.countByUsernameContaining(username);
    }

    @Override
    public List<UserPO> batchGetUsers(List<Long> ids) {
         return userRepository.findByIdIn(ids);
    }
}
