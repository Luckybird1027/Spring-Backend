package com.luckybird.operatelog.service;

import com.luckybird.common.base.PageResult;
import com.luckybird.common.exception.BizException;
import com.luckybird.operatelog.api.req.OperateLogQueryReq;
import com.luckybird.operatelog.api.vo.OperateLogVO;
import com.luckybird.repository.operateLog.OperateLogMapper;
import com.luckybird.repository.operateLog.OperateLogPO;
import com.luckybird.repository.user.UserMapper;
import com.luckybird.repository.user.UserPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 操作日志服务实现类
 *
 * @author 新云鸟
 */
@Service
@RequiredArgsConstructor
public class OperateLogServiceImpl implements OperateLogService {

    private final OperateLogMapper operateLogMapper;

    private final UserMapper userMapper;

    // TODO: VO需通过i18n渲染成对应的语言
    private OperateLogVO toVo(OperateLogPO po) {
        OperateLogVO operateLogVO = new OperateLogVO();
        operateLogVO.setId(po.getId());
        operateLogVO.setOperateModule(po.getOperateModule());
        operateLogVO.setOperateType(po.getOperateType());
        operateLogVO.setOperateFeature(po.getOperateFeature());
        operateLogVO.setDataBrief(po.getDataBrief());
        operateLogVO.setDataDifference(po.getDataDifference());
        operateLogVO.setOperateTime(po.getOperateTime());
        operateLogVO.setClientIp(po.getClientIp());
        operateLogVO.setClientUa(po.getClientUa());
        return operateLogVO;

    }

    private List<OperateLogVO> batchRenderVo(List<OperateLogPO> pos) {
        List<Long> operatorIds = new ArrayList<>();
        for (OperateLogPO po : pos) {
            operatorIds.add(po.getOperatorId());
        }
        List<UserPO> operators = userMapper.selectBatchIds(operatorIds);
        Map<Long, UserPO> operatorMap = operators.stream().collect(Collectors.toMap(UserPO::getId, user -> user));
        List<OperateLogVO> vos = new ArrayList<>();
        for (OperateLogPO po : pos) {
            OperateLogVO operateLogVO = toVo(po);
            UserPO operator = operatorMap.get(po.getOperatorId());
            if (operator == null) {
                throw new BizException("OPERATOR_NOT_EXIST");
            }
            if (StringUtils.hasText(operator.getUsername())) {
                operateLogVO.setOperatorName(operator.getUsername());
            } else {
                operateLogVO.setOperatorName(operator.getAccount());
            }
        }
        return vos;
    }

    @Override
    public OperateLogVO get(Long id) {
        OperateLogPO po = operateLogMapper.selectById(id);
        if (po == null) {
            return new OperateLogVO();
        }
        UserPO operator = userMapper.selectById(po.getOperatorId());
        OperateLogVO operateLogVO = toVo(po);
        if (StringUtils.hasText(operator.getUsername())) {
            operateLogVO.setOperatorName(operator.getUsername());
        } else {
            operateLogVO.setOperatorName(operator.getAccount());
        }

        return operateLogVO;
    }

    @Override
    public List<OperateLogVO> batchGet(Set<Long> ids) {
        List<OperateLogPO> pos = operateLogMapper.selectBatchIds(ids);
        if (pos == null || pos.isEmpty()){
            return new ArrayList<>();
        }
        return batchRenderVo(pos);
    }

    @Override
    public List<OperateLogVO> list(OperateLogQueryReq req) {
        return null;
    }

    @Override
    public PageResult<OperateLogVO> page(OperateLogQueryReq req, Long current, Long rows, boolean searchCount) {
        return null;
    }
}
