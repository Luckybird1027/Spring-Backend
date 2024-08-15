package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.req.*;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.reposity.UserRepository;
import com.luckybird.springbackend.reposity.UserSpecifications;
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
 * 用户服务实现
 *
 * @author 新云鸟
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private UserPO toPo(UserCreateReq req) {
        UserPO userPO = new UserPO();
        userPO.setAccount(req.getAccount());
        userPO.setPassword(req.getPassword());
        userPO.setUsername(req.getUsername());
        userPO.setTelephone(req.getTelephone());
        userPO.setEmail(req.getEmail());
        userPO.setStatus(req.getStatus());
        userPO.setOrganizationId(req.getOrganizationId());
        userPO.setDepartmentId(req.getDepartmentId());
        userPO.setOccupation(req.getOccupation());
        userPO.setRemark(req.getRemark());
        return userPO;
    }

    private UserVO toVO(UserPO po) {
        UserVO userVO = new UserVO();
        userVO.setId(po.getId());
        userVO.setAccount(po.getAccount());
        userVO.setUsername(po.getUsername());
        userVO.setTelephone(po.getTelephone());
        userVO.setEmail(po.getEmail());
        userVO.setStatus(po.getStatus());
        userVO.setOrganizationId(po.getOrganizationId());
        userVO.setDepartmentId(po.getDepartmentId());
        userVO.setOccupation(po.getOccupation());
        userVO.setRemark(po.getRemark());
        return userVO;
    }

    private void poUpdateByReq(UserPO po, UserUpdateReq req) {
        if (req.getAccount() != null) {
            po.setAccount(req.getAccount());
        }
        if (req.getUsername() != null) {
            po.setUsername(req.getUsername());
        }
        if (req.getTelephone() != null) {
            po.setTelephone(req.getTelephone());
        }
        if (req.getEmail() != null) {
            po.setEmail(req.getEmail());
        }
        if (req.getStatus() != null) {
            po.setStatus(req.getStatus());
        }
        if (req.getOrganizationId() != null) {
            po.setOrganizationId(req.getOrganizationId());
        }
        if (req.getDepartmentId() != null) {
            po.setDepartmentId(req.getDepartmentId());
        }
        if (req.getOccupation() != null) {
            po.setOccupation(req.getOccupation());
        }
        if (req.getRemark() != null) {
            po.setRemark(req.getRemark());
        }
    }

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
    public UserVO create(UserCreateReq req) {
        UserPO po = toPo(req);
        Optional<UserPO> existingUser = userRepository.findByAccount(req.getUsername());
        if (existingUser.isPresent()) {
            return toVO(existingUser.get());
        }
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        userRepository.save(po);
        return toVO(po);
    }

    @Override
    public UserVO update(Long id, UserUpdateReq req) {
        UserPO po = userRepository.findById(id).orElseThrow(() -> new BizException(ExceptionMessages.USER_NOT_EXIST));
        poUpdateByReq(po, req);
        userRepository.save(po);
        return toVO(po);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserVO> list(UserQueryReq req) {
        return userRepository.findAll(UserSpecifications.queryByReq(req)).stream().map(this::toVO).toList();
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

    @Override
    public UserVO login(UserLoginReq req) {
        //检查用户是否存在
        Optional<UserPO> existingUser = userRepository.findByAccount(req.getAccount());
        if (existingUser.isEmpty()) {
            throw new BizException(ExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 检查密码是否正确
        UserPO po = existingUser.get();
        if (!passwordEncoder.matches(req.getPassword(), po.getPassword())) {
            throw new BizException(ExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 登录成功
        log.info("User " + req.getAccount() + " logged in successfully");
        return toVO(po);
    }

    @Override
    public void changePassword(Long id, UserChangePasswordReq req){
        // 检查用户是否存在
        Optional<UserPO> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new BizException(ExceptionMessages.USER_NOT_EXIST);
        }
        // 检查旧密码是否正确
        UserPO po = existingUser.get();
        if (!passwordEncoder.matches(req.getOldPassword(), po.getPassword())) {
            throw new BizException(ExceptionMessages.INCORRECT_OLD_PASSWORD);
        }
        // 修改为新密码
        po.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(po);
    }
}
