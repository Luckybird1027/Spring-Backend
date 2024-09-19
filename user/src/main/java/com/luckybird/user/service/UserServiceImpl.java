package com.luckybird.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.auth.base.TokenInfo;
import com.luckybird.auth.service.TokenService;
import com.luckybird.common.base.Difference;
import com.luckybird.common.base.KeyValue;
import com.luckybird.common.base.PageResult;
import com.luckybird.common.base.UserInfo;
import com.luckybird.common.context.utils.ContextUtils;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.json.utils.JsonUtils;
import com.luckybird.log.constant.OperateTypeEnum;
import com.luckybird.log.utils.LogUtils;
import com.luckybird.repository.user.UserMapper;
import com.luckybird.repository.user.UserPO;
import com.luckybird.repository.user.UserStatusEnum;
import com.luckybird.user.api.req.UserChangePasswordReq;
import com.luckybird.user.api.req.UserCreateReq;
import com.luckybird.user.api.req.UserLoginReq;
import com.luckybird.user.api.req.UserQueryReq;
import com.luckybird.user.api.req.UserUpdateReq;
import com.luckybird.user.api.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private final String CURRENT_MODULE = "user";

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

    private UserVO toVo(UserPO po) {
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

    @Async
    protected void differenceLog(UserPO oldPo, UserPO newPo, String feature) {
        if (oldPo == null || newPo == null) {
            return;
        }
        List<Difference> differences = new ArrayList<>();
        if (!oldPo.getAccount().equals(newPo.getAccount())) {
            differences.add(new Difference("account", oldPo.getAccount(), newPo.getAccount()));
        }
        if (!oldPo.getUsername().equals(newPo.getUsername())) {
            differences.add(new Difference("username", oldPo.getUsername(), newPo.getUsername()));
        }
        if (!oldPo.getTelephone().equals(newPo.getTelephone())) {
            differences.add(new Difference("telephone", oldPo.getTelephone(), newPo.getTelephone()));
        }
        if (!oldPo.getEmail().equals(newPo.getEmail())) {
            differences.add(new Difference("email", oldPo.getEmail(), newPo.getEmail()));
        }
        if (!oldPo.getOrganizationId().equals(newPo.getOrganizationId())) {
            differences.add(new Difference("organizationId", oldPo.getOrganizationId(), newPo.getOrganizationId()));
        }
        if (!oldPo.getDepartmentId().equals(newPo.getDepartmentId())) {
            differences.add(new Difference("departmentId", oldPo.getDepartmentId(), newPo.getDepartmentId()));
        }
        if (!oldPo.getOccupation().equals(newPo.getOccupation())) {
            differences.add(new Difference("occupation", oldPo.getOccupation(), newPo.getOccupation()));
        }
        if (!oldPo.getRemark().equals(newPo.getRemark())) {
            differences.add(new Difference("remark", oldPo.getRemark(), newPo.getRemark()));
        }
        if (!differences.isEmpty()) {
            LogUtils.differenceLog(CURRENT_MODULE, OperateTypeEnum.UPDATE.getValue(), feature, differences);
        }
    }

    @Async
    protected void briefLog(UserVO vo, String type, String feature) {
        List<KeyValue> keyValues = new ArrayList<>();
        keyValues.add(new KeyValue("id", vo.getId()));
        keyValues.add(new KeyValue("account", vo.getAccount()));
        keyValues.add(new KeyValue("username", vo.getUsername()));
        keyValues.add(new KeyValue("telephone", vo.getTelephone()));
        keyValues.add(new KeyValue("email", vo.getEmail()));
        keyValues.add(new KeyValue("status", vo.getStatus()));
        keyValues.add(new KeyValue("organizationId", vo.getOrganizationId()));
        keyValues.add(new KeyValue("departmentId", vo.getDepartmentId()));
        keyValues.add(new KeyValue("occupation", vo.getOccupation()));
        keyValues.add(new KeyValue("remark", vo.getRemark()));
        LogUtils.briefLog(CURRENT_MODULE, type, feature, keyValues);
    }

    @Override
    public UserVO get(Long id) {
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            return new UserVO();
        }
        return toVo(po);
    }

    @Override
    public List<UserVO> batchGet(Set<Long> ids) {
        return userMapper.selectBatchIds(ids).stream().map(this::toVo).toList();
    }

    @Override
    public UserVO create(UserCreateReq req) {
        // 检查用户创建请求参数是否合法
        if (req.getStatus() == null) {
            req.setStatus(UserStatusEnum.NORMAL.getKey());
        } else if (UserStatusEnum.of(req.getStatus()) == null) {
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
        briefLog(toVo(po), OperateTypeEnum.CREATE.getValue(), "user.create");
        return toVo(po);
    }

    @Override
    public UserVO update(Long id, UserUpdateReq req) {
        // 检查用户是否存在
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            throw new BizException("USER_NOT_EXIST");
        }
        UserPO oldPo = JsonUtils.deepClone(po);
        // 更新用户信息
        UserPO newPo = updateByReq(po, req);
        newPo.setUpdaterId(ContextUtils.getUserInfo().getId());
        newPo.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(newPo);
        differenceLog(oldPo, newPo, "user.update");
        return toVo(newPo);
    }


    @Override
    public void delete(Long id) {
        UserPO po = userMapper.selectById(id);
        if (po == null) {
            return;
        }
        userMapper.deleteById(id);
        briefLog(toVo(po), OperateTypeEnum.DELETE.getValue(), "user.delete");
    }

    @Override
    public List<UserVO> list(UserQueryReq req) {
        List<UserPO> poList = userMapper.selectList(wrapperByReq(req));
        return poList.stream().map(this::toVo).toList();
    }

    @Override
    public PageResult<UserVO> page(UserQueryReq req, Long current, Long pageSize, boolean searchCount) {
        IPage<UserPO> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<UserPO> wrapper = wrapperByReq(req);
        IPage<UserPO> userPage = userMapper.selectPage(page, wrapper);
        List<UserPO> poList = userPage.getRecords();
        List<UserVO> voList = poList.stream().map(this::toVo).toList();
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
        if (UserStatusEnum.DISABLE.getKey().equals(existingUser.getStatus())) {
            throw new BizException("USER_DISABLED");
        }
        // 登录成功，返回token
        return tokenService.generateToken(existingUser.getId(), toInfo(existingUser));
    }

    @Override
    public void logout(Long userId) {
        UserPO existingUser = userMapper.selectById(userId);
        if (existingUser == null) {
            throw new BizException("USER_NOT_EXIST");
        }
        if (!tokenService.deleteTokenByUserId(userId)) {
            throw new BizException("USER_NOT_LOGIN");
        }
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
        differenceLog(existingUser, newUser, "user.change_password");
    }
}