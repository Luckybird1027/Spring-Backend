package com.luckybird.operatelog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.common.base.Difference;
import com.luckybird.common.base.FieldValue;
import com.luckybird.common.base.PageResult;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.i18n.utils.StringResourceUtils;
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
import java.util.HashSet;
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

    // TODO: 整个Service都无法使用i18n???

    private final UserMapper userMapper;

    private final OperateLogMapper operateLogMapper;

    private OperateLogVO toVo(OperateLogPO po) {
        OperateLogVO operateLogVO = new OperateLogVO();
        operateLogVO.setId(po.getId());
        operateLogVO.setOperateModule(StringResourceUtils.format(po.getOperateModule()));
        operateLogVO.setOperateType(StringResourceUtils.format(po.getOperateType()));
        operateLogVO.setOperateFeature(StringResourceUtils.format(po.getOperateFeature()));
        if (po.getDataBrief() != null) {
            List<FieldValue> fieldValues = po.getDataBrief();
            for (FieldValue fieldValue : fieldValues) {
                fieldValue.setField(StringResourceUtils.format(fieldValue.getField()));
            }
            operateLogVO.setDataBrief(fieldValues);
        }
        if (po.getDataDifference() != null) {
            List<Difference> differences = po.getDataDifference();
            for (Difference difference : differences) {
                difference.setFieldName(StringResourceUtils.format(difference.getFieldName()));
            }
            operateLogVO.setDataDifference(differences);
        }
        operateLogVO.setOperateTime(po.getOperateTime());
        operateLogVO.setClientIp(po.getClientIp());
        operateLogVO.setClientUa(po.getClientUa());
        return operateLogVO;

    }

    private LambdaQueryWrapper<OperateLogPO> wrapperByReq(OperateLogQueryReq req) {
        LambdaQueryWrapper<OperateLogPO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(req.getOperateModule())) {
            wrapper.and(q -> q.like(OperateLogPO::getOperateModule, req.getOperateModule()));
        }
        if (StringUtils.hasText(req.getOperateType())) {
            wrapper.and(q -> q.like(OperateLogPO::getOperateType, req.getOperateType()));
        }
        if (StringUtils.hasText(req.getOperateFeature())) {
            wrapper.and(q -> q.like(OperateLogPO::getOperateFeature, req.getOperateFeature()));
        }
        if (req.getOperatorId() != null) {
            wrapper.and(q -> q.eq(OperateLogPO::getOperatorId, req.getOperatorId()));
        }
        if (req.getStartTime() != null && req.getEndTime() != null && req.getStartTime().isAfter(req.getEndTime())) {
            // TODO: 无法被i18n处理？
            throw new BizException("TIME_RANGE_ERROR");
        }
        if (req.getStartTime() != null) {
            wrapper.and(q -> q.ge(OperateLogPO::getOperateTime, req.getStartTime()));
        }
        if (req.getEndTime() != null) {
            wrapper.and(q -> q.le(OperateLogPO::getOperateTime, req.getEndTime()));
        }
        return wrapper;
    }


    private List<OperateLogVO> batchRenderVo(List<OperateLogPO> pos) {
        Set<Long> operatorIds = new HashSet<>();
        for (OperateLogPO po : pos) {
            operatorIds.add(po.getOperatorId());
        }
        if (operatorIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserPO> operators = userMapper.selectBatchIds(operatorIds);
        Map<Long, UserPO> operatorMap = operators.stream().collect(Collectors.toMap(UserPO::getId, user -> user));
        List<OperateLogVO> vos = new ArrayList<>();
        for (OperateLogPO po : pos) {
            OperateLogVO operateLogVO = toVo(po);
            UserPO operator = operatorMap.get(po.getOperatorId());
            if (operator != null) {
                if (StringUtils.hasText(operator.getUsername())) {
                    operateLogVO.setOperatorName(operator.getUsername());
                } else {
                    operateLogVO.setOperatorName(operator.getAccount());
                }
            } else {
                operateLogVO.setOperatorName(StringResourceUtils.format("unknown"));
            }
            vos.add(operateLogVO);
        }
        return vos;
    }

    @Override
    public OperateLogVO get(Long id) {
        OperateLogPO po = operateLogMapper.selectById(id);
        if (po == null) {
            return new OperateLogVO();
        }
        OperateLogVO operateLogVO = toVo(po);
        UserPO operator = userMapper.selectById(po.getOperatorId());
        if (operator != null) {
            if (StringUtils.hasText(operator.getUsername())) {
                operateLogVO.setOperatorName(operator.getUsername());
            } else {
                operateLogVO.setOperatorName(operator.getAccount());
            }
        } else {
            operateLogVO.setOperatorName(StringResourceUtils.format("unknown"));
        }
        return operateLogVO;
    }

    @Override
    public List<OperateLogVO> batchGet(Set<Long> ids) {
        List<OperateLogPO> pos = operateLogMapper.selectBatchIds(ids);
        if (pos == null || pos.isEmpty()) {
            return new ArrayList<>();
        }
        return batchRenderVo(pos);
    }

    @Override
    public List<OperateLogVO> list(OperateLogQueryReq req) {
        List<OperateLogPO> pos = operateLogMapper.selectList(wrapperByReq(req));
        if (pos == null || pos.isEmpty()) {
            return new ArrayList<>();
        }
        return batchRenderVo(pos);
    }

    @Override
    public PageResult<OperateLogVO> page(OperateLogQueryReq req, Long current, Long pageSize, boolean searchCount) {
        IPage<OperateLogPO> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<OperateLogPO> wrapper = wrapperByReq(req);
        IPage<OperateLogPO> userPage = operateLogMapper.selectPage(page, wrapper);
        List<OperateLogPO> poList = userPage.getRecords();
        List<OperateLogVO> voList = batchRenderVo(poList);
        if (searchCount) {
            return new PageResult<>(userPage.getTotal(), voList);
        } else {
            return new PageResult<>(voList);
        }
    }
}
