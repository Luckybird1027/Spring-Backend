package com.luckybird.operatelog.service;

import com.luckybird.common.base.PageResult;
import com.luckybird.operatelog.api.req.OperateLogQueryReq;
import com.luckybird.operatelog.api.vo.OperateLogVO;
import com.luckybird.repository.operateLog.OperateLogMapper;
import com.luckybird.repository.operateLog.OperateLogPO;
import com.luckybird.repository.user.UserMapper;
import com.luckybird.repository.user.UserPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

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
        return null;
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
