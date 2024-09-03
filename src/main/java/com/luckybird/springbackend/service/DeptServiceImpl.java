package com.luckybird.springbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.springbackend.api.req.DeptCreateReq;
import com.luckybird.springbackend.api.req.DeptQueryReq;
import com.luckybird.springbackend.api.req.DeptUpdateReq;
import com.luckybird.springbackend.api.vo.DeptTreeVO;
import com.luckybird.springbackend.api.vo.DeptVO;
import com.luckybird.springbackend.common.base.PageResult;
import com.luckybird.springbackend.common.utils.ContextUtils;
import com.luckybird.springbackend.common.utils.StringResourceUtils;
import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.mapper.DeptMapper;
import com.luckybird.springbackend.po.DeptPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 部门服务实现
 *
 * @author 新云鸟
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;

    private DeptVO toVO(DeptPO po) {
        DeptVO vo = new DeptVO();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setParentId(po.getParentId());
        vo.setRemark(po.getRemark());
        return vo;
    }

    private DeptPO toPO(DeptCreateReq req) {
        DeptPO po = new DeptPO();
        po.setName(req.getName());
        po.setParentId(req.getParentId());
        po.setRemark(req.getRemark());
        return po;

    }

    private DeptPO updateByReq(DeptPO po, DeptUpdateReq req) {
        Optional.ofNullable(req.getName()).ifPresent(po::setName);
        Optional.ofNullable(req.getParentId()).ifPresent(po::setParentId);
        Optional.ofNullable(req.getRemark()).ifPresent(po::setRemark);
        return po;
    }

    private LambdaQueryWrapper<DeptPO> wrapperByReq(DeptQueryReq req) {
        LambdaQueryWrapper<DeptPO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(req.getKeyword())) {
            wrapper.and(q -> q.like(DeptPO::getName, "%" + req.getKeyword() + "%")
                    .or().like(DeptPO::getRemark, "%" + req.getKeyword() + "%"));
        }
        if (req.getParentId() != null) {
            wrapper.and(q -> q.eq(DeptPO::getParentId, req.getParentId()));
        }
        return wrapper;
    }

    @Override
    public DeptVO get(Long id) {
        DeptPO po = deptMapper.selectById(id);
        if (po == null) {
            return new DeptVO();
        }
        return toVO(po);
    }

    @Override
    public List<DeptVO> batchGet(Set<Long> ids) {
        return deptMapper.selectBatchIds(ids).stream().map(this::toVO).toList();
    }

    @Override
    public DeptVO create(DeptCreateReq req) {
        // 检查父部门是否存在
        if (req.getParentId() != null) {
            DeptPO parentDept = deptMapper.selectById(req.getParentId());
            if (parentDept == null) {
                throw new BizException(StringResourceUtils.format("PARENT_DEPT_NOT_EXIST"));
            }
        }
        // 填充信息并插入数据库
        DeptPO po = toPO(req);
        po.setCreatorId(ContextUtils.getUserInfo().getId());
        po.setCreateTime(LocalDateTime.now());
        deptMapper.insert(po);
        return toVO(po);
    }

    @Override
    public DeptVO update(Long id, DeptUpdateReq req) {
        // 检查更新对象是否为根部门
        if (id == 0) {
            throw new BizException(StringResourceUtils.format("ROOT_DEPT_CANNOT_UPDATE"));
        }
        // 检查部门是否存在
        DeptPO po = deptMapper.selectById(id);
        if (po == null) {
            throw new BizException(StringResourceUtils.format("DEPT_NOT_EXIST"));
        }
        // 更新部门信息
        DeptPO updatePo = updateByReq(po, req);
        updatePo.setId(id);
        updatePo.setUpdaterId(ContextUtils.getUserInfo().getId());
        updatePo.setUpdateTime(LocalDateTime.now());
        deptMapper.updateById(updatePo);
        return toVO(updatePo);
    }

    @Override
    public void delete(Long id) {
        // 检查删除对象是否为根部门
        if (id == 0) {
            throw new BizException(StringResourceUtils.format("ROOT_DEPT_CANNOT_DELETE"));
        }
        // 删除部门
        deptMapper.deleteById(id);
    }

    @Override
    public List<DeptVO> list(DeptQueryReq req) {
        List<DeptPO> poList = deptMapper.selectList(wrapperByReq(req));
        return poList.stream().map(this::toVO).toList();
    }

    @Override
    public PageResult<DeptVO> page(DeptQueryReq req, Long current, Long pageSize, boolean searchCount) {
        IPage<DeptPO> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<DeptPO> wrapper = wrapperByReq(req);
        IPage<DeptPO> deptPage = deptMapper.selectPage(page, wrapper);
        List<DeptPO> poList = deptPage.getRecords();
        List<DeptVO> voList = poList.stream().map(this::toVO).toList();
        if (searchCount) {
            return new PageResult<>(deptPage.getTotal(), voList);
        } else {
            return new PageResult<>(voList);
        }
    }

    @Override
    public DeptTreeVO getDeptTree() {
        return null;
    }

    @Override
    public DeptTreeVO getDeptTree(Long id) {
        return null;
    }
}
