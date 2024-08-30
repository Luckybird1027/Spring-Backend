package com.luckybird.springbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.springbackend.api.req.*;
import com.luckybird.springbackend.api.vo.UserVO;
import com.luckybird.springbackend.common.base.PageResult;
import com.luckybird.springbackend.common.base.TokenInfo;
import com.luckybird.springbackend.common.base.UserInfo;
import com.luckybird.springbackend.common.util.ContextUtil;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.common.constant.ErrorInfoEnum;
import com.luckybird.springbackend.mapper.UserMapper;
import com.luckybird.springbackend.po.UserPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
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
        po.setStatus(req.getStatus().byteValue());
        po.setOrganizationId(req.getOrganizationId());
        po.setDepartmentId(req.getDepartmentId());
        po.setOccupation(req.getOccupation());
        po.setRemark(req.getRemark());
        return po;
    }

    private UserPO toPo(UserUpdateReq req) {
        UserPO po = new UserPO();
        po.setAccount(req.getAccount());
        po.setPassword(req.getPassword());
        po.setUsername(req.getUsername());
        po.setTelephone(req.getTelephone());
        po.setEmail(req.getEmail());
        if (req.getStatus() != null) {
            po.setStatus(req.getStatus().byteValue());
        }
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
        userVO.setStatus(po.getStatus().intValue());
        userVO.setOrganizationId(po.getOrganizationId());
        userVO.setDepartmentId(po.getDepartmentId());
        userVO.setOccupation(po.getOccupation());
        userVO.setRemark(po.getRemark());
        return userVO;
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
            wrapper.and(q -> q.eq(UserPO::getStatus, req.getStatus().byteValue()));
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
        // 检查用户是否已存在
        UserPO po = toPo(req);
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<UserPO>().eq(UserPO::getAccount, req.getAccount());
        UserPO existingUser = userMapper.selectOne(wrapper);
        if (existingUser != null) {
            throw new BizException(ErrorInfoEnum.ACCOUNT_ALREADY_EXISTS);
        }
        // 填充信息并创建用户
        po.setPassword(passwordEncoder.encode(po.getPassword()));
        po.setCreatorId(ContextUtil.getUserInfo().getId());
        po.setCreateTime(LocalDateTime.now());
        userMapper.insert(po);
        return toVO(po);
    }

    @Override
    public UserVO update(Long id, UserUpdateReq req) {
        // 检查用户是否存在
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            throw new BizException(ErrorInfoEnum.USER_NOT_EXIST);
        }
        // 更新用户信息
        UserPO updatePo = toPo(req);
        updatePo.setId(id);
        updatePo.setUpdaterId(ContextUtil.getUserInfo().getId());
        updatePo.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(updatePo);
        return toVO(userMapper.selectById(id));
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
            throw new BizException(ErrorInfoEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 检查密码是否正确
        if (!passwordEncoder.matches(req.getPassword(), existingUser.getPassword())) {
            throw new BizException(ErrorInfoEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 检查用户是否被禁用
        if (existingUser.getStatus() == 1) {
            throw new BizException(ErrorInfoEnum.USER_DISABLED);
        }
        // 登录成功，返回token
        log.info("User " + req.getAccount() + " logged in successfully");
        return tokenService.generateToken(existingUser.getId(), toInfo(existingUser));
    }

    @Override
    public void logout(Long userId) {
        if (!tokenService.deleteTokenByUserId(userId)) {
            throw new BizException(ErrorInfoEnum.USER_NOT_LOGIN);
        }
        log.info("User " + userId + " logged out successfully");
    }

    @Override
    public void changePassword(Long userId, UserChangePasswordReq req) {
        // 检查用户是否存在
        UserPO existingUser = userMapper.selectById(userId);
        if (existingUser == null) {
            throw new BizException(ErrorInfoEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        // 检查旧密码是否正确
        if (!passwordEncoder.matches(req.getOldPassword(), existingUser.getPassword())) {
            throw new BizException(ErrorInfoEnum.INCORRECT_OLD_PASSWORD);
        }
        // 修改为新密码
        UserPO newUser = new UserPO();
        newUser.setId(userId);
        newUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userMapper.updateById(newUser);
    }
}