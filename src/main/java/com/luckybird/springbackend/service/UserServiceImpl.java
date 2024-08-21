package com.luckybird.springbackend.service;

import com.luckybird.springbackend.api.req.*;
import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.reposity.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    private final TokenService tokenService;

    private UserPO toPo(UserCreateReq req) {
        UserPO userPO = new UserPO();
        userPO.setAccount(req.getAccount());
        userPO.setPassword(req.getPassword());
        userPO.setUsername(req.getUsername());
        userPO.setTelephone(req.getTelephone());
        userPO.setEmail(req.getEmail());
        userPO.setStatus(req.getStatus().byteValue());
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
        userVO.setStatus(po.getStatus().intValue());
        userVO.setOrganizationId(po.getOrganizationId());
        userVO.setDepartmentId(po.getDepartmentId());
        userVO.setOccupation(po.getOccupation());
        userVO.setRemark(po.getRemark());
        return userVO;
    }

    private void poUpdateByReq(UserPO po, UserUpdateReq req) {
        Optional.ofNullable(req.getAccount()).ifPresent(po::setAccount);
        Optional.ofNullable(req.getUsername()).ifPresent(po::setUsername);
        Optional.ofNullable(req.getTelephone()).ifPresent(po::setTelephone);
        Optional.ofNullable(req.getEmail()).ifPresent(po::setEmail);
        Optional.ofNullable(req.getStatus()).ifPresent(status -> po.setStatus(status.byteValue()));
        Optional.ofNullable(req.getOrganizationId()).ifPresent(po::setOrganizationId);
        Optional.ofNullable(req.getDepartmentId()).ifPresent(po::setDepartmentId);
        Optional.ofNullable(req.getOccupation()).ifPresent(po::setOccupation);
        Optional.ofNullable(req.getRemark()).ifPresent(po::setRemark);
    }

    private Specification<UserPO> queryByReq(UserQueryReq req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(req.getKeyword())) {
                // TODO: 防注入
                Predicate accountPredicate = cb.like(root.get("account"), "%" + req.getKeyword() + "%");
                Predicate usernamePredicate = cb.like(root.get("username"), "%" + req.getKeyword() + "%");
                Predicate telephonePredicate = cb.like(root.get("telephone"), "%" + req.getKeyword() + "%");
                Predicate emailPredicate = cb.like(root.get("email"), "%" + req.getKeyword() + "%");
                Predicate remarkPredicate = cb.like(root.get("remark"), "%" + req.getKeyword() + "%");
                predicates.add(cb.or(accountPredicate, usernamePredicate, telephonePredicate, emailPredicate, remarkPredicate));
            }
            if (req.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), req.getStatus()));
            }
            if (req.getOrganizationId() != null){
                predicates.add(cb.equal(root.get("organizationId"), req.getOrganizationId()));
            }
            if (req.getDepartmentId() != null){
                predicates.add(cb.equal(root.get("departmentId"), req.getDepartmentId()));
            }
            if (StringUtils.hasText(req.getOccupation())){
                // TODO: 用JSON方法模糊查询
                predicates.add(cb.like(root.get("occupation"), "%" + req.getOccupation() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public UserVO get(Long id) {
        UserPO po = userRepository.findById(id).orElse(new UserPO());
        return toVO(po);
    }

    @Override
    public List<UserVO> batchGet(Set<Long> ids) {
        return userRepository.findAllById(ids).stream().map(this::toVO).toList();
    }

    @Override
    public UserVO create(UserCreateReq req) {
        UserPO po = toPo(req);
        Optional<UserPO> existingUser = userRepository.findByAccount(req.getUsername());
        if (existingUser.isPresent()) {
            throw new BizException(ExceptionMessages.ACCOUNT_ALREADY_EXISTS);
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
        return userRepository.findAll(queryByReq(req)).stream().map(this::toVO).toList();
    }

    @Override
    public PageResult<UserVO> page(UserQueryReq req, int current, int pageSize, boolean searchCount) {
        Pageable pageable = PageRequest.of(current - 1, pageSize);
        Specification<UserPO> spec = queryByReq(req);
        List<UserPO> pos = userRepository.findAll(spec, pageable).getContent();
        if (searchCount) {
            long count = userRepository.count(spec);
            return new PageResult<>(count, pos.stream().map(this::toVO).toList());
        }
        return new PageResult<>(pos.stream().map(this::toVO).toList());
    }

    @Override
    public TokenVO login(UserLoginReq req) {
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
        // 登录成功，发放token
        log.info("User " + req.getAccount() + " logged in successfully");
        /* TODO: 需检查是否重复登录（redis是否已存在token），若已登录则报业务异常
         * 如果要检测是否登录，则需要在登录时同时插入一个（ID,accessToken）的键值对以供查询，在TokenService中增加登录状态检查，并在UserService中调用TokenService的登录方法
         * 基于上面的设计还可以增加logout的功能
         */
        return tokenService.generateToken(po.getId());
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
