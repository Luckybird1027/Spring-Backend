package com.luckybird.springbackend.service;

import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.reposity.UserRepository;
import com.luckybird.springbackend.api.req.UserCreateReq;
import com.luckybird.springbackend.api.req.UserQueryReq;
import com.luckybird.springbackend.api.req.UserUpdateReq;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.api.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 新云鸟
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private UserPO toPo(UserCreateReq req) {
        UserPO po = new UserPO();
        po.setUsername(req.getUsername());
        po.setPassword(req.getPassword());
        return po;
    }

    public UserVO toVO(UserPO po) {
        UserVO vo = new UserVO();
        vo.setId(po.getId());
        vo.setUsername(po.getUsername());
        return vo;
    }

//    @Override
//    public UserVO register(UserCreateReq req) {
//        // 检查用户是否存在
//        Optional<UserPO> existingUser = userRepository.findByUsername(req.getUsername());
//        if (existingUser.isPresent()) {
//            throw new BizException(ExceptionMessages.USERNAME_ALREADY_EXISTS);
//        }
//        // 检查完成，注册用户
//        UserPO po = toPO(req);
//        po.setPassword(passwordEncoder.encode(req.getPassword()));
//        userRepository.save(po);
//        log.info("User " + po.getUsername() + " registered successfully");
//        return toVO(po);
//    }
//
//    @Override
//    public UserVO login(UserLoginReq req) {
//        //检查用户是否存在
//        Optional<UserPO> existingUser = userRepository.findByUsername(req.getUsername());
//        if (existingUser.isEmpty()) {
//            throw new BizException(ExceptionMessages.USER_NOT_EXIST);
//        }
//        // 检查密码是否正确
//        UserPO po = existingUser.get();
//        if (!passwordEncoder.matches(req.getPassword(), po.getPassword())) {
//            throw new BizException(ExceptionMessages.INCORRECT_PASSWORD);
//        }
//        // 登录成功
//        log.info("User " + req.getUsername() + " logged in successfully");
//        return toVO(po);
//    }

    @Override
    public UserVO get(Long id) {
        UserPO po = userRepository.findById(id).orElse(new UserPO());
        return toVO(po);
    }

    @Override
    public List<UserVO> batchGet(Set<Long> ids) {
        return userRepository.findByIdIn(ids).stream().map(this::toVO).toList();
    }

    @Override
    public UserVO create(UserCreateReq dto) {
        UserPO po = toPo(dto);
        Optional<UserPO> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            return toVO(existingUser.get());
        }
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        userRepository.save(po);
        return toVO(po);
    }

    // TODO: 更改密码操作应该单独实现一个接口
    // TODO: update操作只修改userDto中不为空的的属性，其他属性不修改
    @Override
    public UserVO update(Long id, UserUpdateReq dto) {
        UserPO po = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        po.setUsername(dto.getUsername());
        userRepository.save(po);
        return toVO(po);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserVO> list(UserQueryReq req) {
        return userRepository.findByUsernameContaining(req.getKeyword()).stream().map(this::toVO).toList();
    }

    @Override
    public PageResult<UserVO> page(UserQueryReq req, int current, int pageSize, boolean searchCount) {
        Pageable pageable = PageRequest.of(current - 1, pageSize);
        List<UserPO> pos = userRepository.findByUsernameContaining(req.getKeyword(), pageable);
        if (searchCount) {
            long count = userRepository.countByUsernameContaining(req.getKeyword());

            return new PageResult<>(count, pos.stream().map(this::toVO).toList());
        }
        return new PageResult<>(pos.stream().map(this::toVO).toList());
    }
}
