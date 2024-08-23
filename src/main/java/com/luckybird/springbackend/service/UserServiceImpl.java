package com.luckybird.springbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.springbackend.api.req.*;
import com.luckybird.springbackend.api.vo.TokenVO;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.base.PageResult;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.ExceptionMessages;
import com.luckybird.springbackend.mapper.UserMapper;
import com.luckybird.springbackend.po.UserPO;
import com.luckybird.springbackend.reposity.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserMapper userMapper;

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

    private UserPO toPo(UserUpdateReq req) {
        UserPO userPO = new UserPO();
        userPO.setId(req.getId());
        userPO.setAccount(req.getAccount());
        userPO.setPassword(req.getPassword());
        userPO.setUsername(req.getUsername());
        userPO.setTelephone(req.getTelephone());
        userPO.setEmail(req.getEmail());
        if (req.getStatus() != null) {
            userPO.setStatus(req.getStatus().byteValue());
        }
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

    private UserPO poUpdateByReq(UserPO po, UserUpdateReq req) {
        Optional.ofNullable(req.getAccount()).ifPresent(po::setAccount);
        Optional.ofNullable(req.getUsername()).ifPresent(po::setUsername);
        Optional.ofNullable(req.getTelephone()).ifPresent(po::setTelephone);
        Optional.ofNullable(req.getEmail()).ifPresent(po::setEmail);
        Optional.ofNullable(req.getStatus()).ifPresent(status -> po.setStatus(status.byteValue()));
        Optional.ofNullable(req.getOrganizationId()).ifPresent(po::setOrganizationId);
        Optional.ofNullable(req.getDepartmentId()).ifPresent(po::setDepartmentId);
        Optional.ofNullable(req.getOccupation()).ifPresent(po::setOccupation);
        Optional.ofNullable(req.getRemark()).ifPresent(po::setRemark);
        return po;
    }

    private Specification<UserPO> specificationByReq(UserQueryReq req) {
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
            if (req.getOrganizationId() != null) {
                predicates.add(cb.equal(root.get("organizationId"), req.getOrganizationId()));
            }
            if (req.getDepartmentId() != null) {
                predicates.add(cb.equal(root.get("departmentId"), req.getDepartmentId()));
            }
            if (StringUtils.hasText(req.getOccupation())) {
                // TODO: 用JSON方法模糊查询
                predicates.add(cb.like(root.get("occupation"), "%" + req.getOccupation() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private QueryWrapper<UserPO> wrapperByReq(UserQueryReq req) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(req.getKeyword())) {
            // TODO: 防注入
            wrapper.like("account", "%" + req.getKeyword() + "%");
            wrapper.or().like("username", "%" + req.getKeyword() + "%");
            wrapper.or().like("telephone", "%" + req.getKeyword() + "%");
            wrapper.or().like("email", "%" + req.getKeyword() + "%");
            wrapper.or().like("remark", "%" + req.getKeyword() + "%");
        }
        if (req.getStatus() != null) {
            wrapper.eq("status", req.getStatus());
        }
        if (req.getOrganizationId() != null) {
            wrapper.eq("organization_id", req.getOrganizationId());
        }
        if (req.getDepartmentId() != null) {
            wrapper.eq("department_id", req.getDepartmentId());
        }
        if (StringUtils.hasText(req.getOccupation())) {
            // TODO: 用JSON方法模糊查询
            wrapper.like("occupation", "%" + req.getOccupation() + "%");
        }
        return wrapper;
    }

    @Override
    public UserVO get(Long id) {
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            return new UserVO();
        }
        return toVO(po);
    }

    @Override
    public List<UserVO> batchGet(Set<Long> ids) {
        return userMapper.selectBatchIds(ids).stream().map(this::toVO).toList();
    }

    @Override
    public UserVO create(UserCreateReq req) {
        UserPO po = toPo(req);
        QueryWrapper<UserPO> wrapper = new QueryWrapper<UserPO>().eq("account", req.getAccount());
        UserPO existingUser = userMapper.selectOne(wrapper);
        if (existingUser != null) {
            throw new BizException(ExceptionMessages.ACCOUNT_ALREADY_EXISTS);
        }
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        userMapper.insert(po);
        return toVO(po);
    }

    @Override
    public UserVO update(Long id, UserUpdateReq req) {
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            throw new BizException(ExceptionMessages.USER_NOT_EXIST);
        }
        UpdateWrapper<UserPO> wrapper = new UpdateWrapper<UserPO>().eq("id", id);
        userMapper.update(toPo(req), wrapper);
        return toVO(po);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public List<UserVO> list(UserQueryReq req) {
        return userMapper.selectList(wrapperByReq(req)).stream().map(this::toVO).toList();
    }

    @Override
    public PageResult<UserVO> page(UserQueryReq req, Long current, Long pageSize, boolean searchCount) {
        IPage<UserPO> page = new Page<>(current, pageSize);
        QueryWrapper<UserPO> wrapper = wrapperByReq(req);
        // TODO: page完全摆设吧，一点分页的事情都没干
        IPage<UserPO> userPage = userMapper.selectPage(page, wrapper);
        List<UserPO> userList = userPage.getRecords();
        Long total = userPage.getTotal();
        List<UserVO> userVOList = userList.stream().map(this::toVO).toList();
        if (searchCount) {
            return new PageResult<>(total, userVOList);
        } else {
            return new PageResult<>(userVOList);
        }
    }

    @Override
    public TokenVO login(UserLoginReq req) {
        // 检查用户是否存在
        Optional<UserPO> existingUser = userRepository.findByAccount(req.getAccount());
        if (existingUser.isEmpty()) {
            throw new BizException(ExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 检查密码是否正确
        UserPO po = existingUser.get();
        if (!passwordEncoder.matches(req.getPassword(), po.getPassword())) {
            throw new BizException(ExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 检查用户是否被禁用
        if (existingUser.get().getStatus() == 1) {
            throw new BizException(ExceptionMessages.USER_DISABLED);
        }
        // 检查是否重复登录
        if (tokenService.findTokenByUserId(po.getId()) != null) {
            throw new BizException(ExceptionMessages.USER_ALREADY_LOGIN);
        }
        // 登录成功，返回token
        log.info("User " + req.getAccount() + " logged in successfully");
        return tokenService.generateToken(po.getId());
    }

    @Override
    public void logout(String rawToken) {
        // 处理token头部
        String accessToken = tokenService.extractToken(rawToken);
        // 从token获取用户ID
        Long userId = tokenService.verifyToken(accessToken).getUserId();
        if (!tokenService.deleteTokenByUserId(userId)) {
            throw new BizException("user not login");
        }
        log.info("User " + userId + " logged out successfully");
    }

    @Override
    public void changePassword(String rawToken, UserChangePasswordReq req) {
        // 处理token头部
        String accessToken = tokenService.extractToken(rawToken);
        // 从token获取用户ID
        Long id = tokenService.verifyToken(accessToken).getUserId();
        if (id == null) {
            throw new BizException(ExceptionMessages.UNAUTHORIZED_ACCESS);
        }
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
