package com.luckybird.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.auth.base.TokenInfo;
import com.luckybird.auth.service.TokenService;
import com.luckybird.common.base.PageResult;
import com.luckybird.common.base.UserInfo;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.utils.ContextUtils;
import com.luckybird.user.api.req.UserChangePasswordReq;
import com.luckybird.user.api.req.UserCreateReq;
import com.luckybird.user.api.req.UserLoginReq;
import com.luckybird.user.api.req.UserQueryReq;
import com.luckybird.user.api.req.UserUpdateReq;
import com.luckybird.user.api.vo.UserVO;
import com.luckybird.user.constant.StatusEnum;
import com.luckybird.user.mapper.UserMapper;
import com.luckybird.user.po.UserPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private UserPO toPo(UserCreateReq req) {
        UserPO po = new UserPO();
        po.setAccount(req.getAccount());
        po.setPassword(req.getPassword());
        po.setUsername(req.getUsername());
        po.setTelephone(req.getTelephone());
        po.setEmail(req.getEmail());
        po.setStatus(req.getStatus());
        po.setOrganizationId(req.getOrganizationId());
        po.setDepartmentId(req.getDepartmentId());
        po.setOccupation(req.getOccupation());
        po.setRemark(req.getRemark());
        return po;
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

    private UserPO updateByReq(UserPO po, UserUpdateReq req) {
        Optional.ofNullable(req.getAccount()).ifPresent(po::setAccount);
        Optional.ofNullable(req.getUsername()).ifPresent(po::setUsername);
        Optional.ofNullable(req.getTelephone()).ifPresent(po::setTelephone);
        Optional.ofNullable(req.getEmail()).ifPresent(po::setEmail);
        Optional.ofNullable(req.getOrganizationId()).ifPresent(po::setOrganizationId);
        Optional.ofNullable(req.getDepartmentId()).ifPresent(po::setDepartmentId);
        Optional.ofNullable(req.getOccupation()).ifPresent(po::setOccupation);
        Optional.ofNullable(req.getRemark()).ifPresent(po::setRemark);
        return po;
    }

    UserInfo toInfo(UserPO po) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(po.getId());
        userInfo.setAccount(po.getAccount());
        userInfo.setUsername(po.getUsername());
        userInfo.setOrganizationId(po.getOrganizationId());
        userInfo.setDepartmentId(po.getDepartmentId());
        return userInfo;

    }

    private LambdaQueryWrapper<UserPO> wrapperByReq(UserQueryReq req) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(req.getKeyword())) {
            wrapper.and(q -> q.like(UserPO::getAccount, "%" + req.getKeyword() + "%")
                    .or().like(UserPO::getUsername, "%" + req.getKeyword() + "%")
                    .or().like(UserPO::getTelephone, "%" + req.getKeyword() + "%")
                    .or().like(UserPO::getEmail, "%" + req.getKeyword() + "%")
                    .or().like(UserPO::getRemark, "%" + req.getKeyword() + "%"));
        }
        if (req.getStatus() != null) {
            wrapper.and(q -> q.eq(UserPO::getStatus, req.getStatus()));
        }
        if (req.getOrganizationId() != null) {
            wrapper.and(q -> q.eq(UserPO::getOrganizationId, req.getOrganizationId()));
        }
        if (req.getDepartmentId() != null) {
            wrapper.and(q -> q.eq(UserPO::getDepartmentId, req.getDepartmentId()));
        }
        if (StringUtils.hasText(req.getOccupation())) {

            wrapper.and(q -> q.apply("JSON_CONTAINS(occupation, JSON_QUOTE({0}), '$')", req.getOccupation()));
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
        // 检查用户创建请求参数是否合法
        if (req.getStatus() == null) {
            req.setStatus(StatusEnum.NORMAL.getKey());
        } else if (StatusEnum.of(req.getStatus()) == null) {
            throw new BizException("INVALID_PARAMETER");
        }
        // 检查用户是否已存在
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<UserPO>().eq(UserPO::getAccount, req.getAccount());
        UserPO existingUser = userMapper.selectOne(wrapper);
        if (existingUser != null) {
            throw new BizException("ACCOUNT_ALREADY_EXISTS");
        }
        // 填充信息并创建用户
        UserPO po = toPo(req);
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        po.setCreatorId(ContextUtils.getUserInfo().getId());
        po.setCreateTime(LocalDateTime.now());
        userMapper.insert(po);
        return toVO(po);
    }

    @Override
    public UserVO update(Long id, UserUpdateReq req) {
        // 检查用户是否存在
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            throw new BizException("USER_NOT_EXIST");
        }
        // 更新用户信息
        UserPO updatePo = updateByReq(po, req);
        updatePo.setUpdaterId(ContextUtils.getUserInfo().getId());
        updatePo.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(updatePo);
        return toVO(updatePo);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public List<UserVO> list(UserQueryReq req) {
        List<UserPO> poList = userMapper.selectList(wrapperByReq(req));
        return poList.stream().map(this::toVO).toList();
    }

    @Override
    public PageResult<UserVO> page(UserQueryReq req, Long current, Long pageSize, boolean searchCount) {
        IPage<UserPO> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<UserPO> wrapper = wrapperByReq(req);
        IPage<UserPO> userPage = userMapper.selectPage(page, wrapper);
        List<UserPO> poList = userPage.getRecords();
        List<UserVO> voList = poList.stream().map(this::toVO).toList();
        if (searchCount) {
            return new PageResult<>(userPage.getTotal(), voList);
        } else {
            return new PageResult<>(voList);
        }
    }

    @Override
    public TokenInfo login(UserLoginReq req) {
        // 检查用户是否存在
        UserPO existingUser = userMapper.selectOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getAccount, req.getAccount()));
        if (existingUser == null) {
            throw new BizException("INCORRECT_ACCOUNT_OR_PASSWORD");
        }
        // 检查密码是否正确
        if (!passwordEncoder.matches(req.getPassword(), existingUser.getPassword())) {
            throw new BizException("INCORRECT_ACCOUNT_OR_PASSWORD");
        }
        // 检查用户是否被禁用
        if (StatusEnum.DISABLE.getKey().equals(existingUser.getStatus())) {
            throw new BizException("USER_DISABLED");
        }
        // 登录成功，返回token
        log.info("User " + req.getAccount() + " logged in successfully");
        return tokenService.generateToken(existingUser.getId(), toInfo(existingUser));
    }

    @Override
    public void logout(Long userId) {
        if (!tokenService.deleteTokenByUserId(userId)) {
            throw new BizException("USER_NOT_LOGIN");
        }
        log.info("User " + userId + " logged out successfully");
    }

    @Override
    public void changePassword(Long userId, UserChangePasswordReq req) {
        // 检查用户是否存在
        UserPO existingUser = userMapper.selectById(userId);
        if (existingUser == null) {
            throw new BizException("INCORRECT_ACCOUNT_OR_PASSWORD");
        }
        // 检查旧密码是否正确
        if (!passwordEncoder.matches(req.getOldPassword(), existingUser.getPassword())) {
            throw new BizException("INCORRECT_OLD_PASSWORD");
        }
        // 修改为新密码
        UserPO newUser = new UserPO();
        newUser.setId(userId);
        newUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userMapper.updateById(newUser);
    }
}