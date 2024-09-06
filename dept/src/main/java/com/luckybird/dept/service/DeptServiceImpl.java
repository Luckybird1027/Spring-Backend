package com.luckybird.dept.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.common.base.PageResult;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.utils.ContextUtils;
import com.luckybird.dept.api.req.DeptCreateReq;
import com.luckybird.dept.api.req.DeptQueryReq;
import com.luckybird.dept.api.req.DeptUpdateReq;
import com.luckybird.dept.api.vo.DeptTreeVO;
import com.luckybird.dept.api.vo.DeptVO;
import com.luckybird.dept.mapper.DeptMapper;
import com.luckybird.dept.po.DeptPO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        vo.setPath(po.getPath());
        vo.setRemark(po.getRemark());
        return vo;
    }

    private DeptTreeVO toDeptTreeVO(DeptPO po) {
        DeptTreeVO vo = new DeptTreeVO();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setParentId(po.getParentId());
        vo.setPath(po.getPath());
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

    private void addChild(DeptTreeVO parent, DeptTreeVO child) {
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
        }
        List<DeptTreeVO> children = parent.getChildren();
        children.add(child);
        parent.setChildren(children);
    }

    private List<DeptTreeVO> buildDeptTree(List<DeptPO> deptList) {
        return deptList.stream().map(this::setTree).toList();
    }

    @NotNull
    private DeptTreeVO setTree(DeptPO dept) {
        DeptTreeVO deptTree = new DeptTreeVO();
        deptTree.setId(dept.getId());
        deptTree.setName(dept.getName());
        deptTree.setParentId(dept.getParentId());
        deptTree.setPath(dept.getPath());
        deptTree.setRemark(dept.getRemark());
        deptTree.setChildren(buildDeptTree(deptMapper.selectList(new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getParentId, dept.getId()))));
        return deptTree;
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
        DeptPO parentDept = deptMapper.selectById(req.getParentId());
        if (req.getParentId() != null) {
            if (parentDept == null) {
                throw new BizException("PARENT_DEPT_NOT_EXIST");
            }
        }
        // 填充信息并插入数据库
        DeptPO po = toPO(req);
        po.setCreatorId(ContextUtils.getUserInfo().getId());
        po.setCreateTime(LocalDateTime.now());
        deptMapper.insert(po);
        po.setPath(parentDept.getPath() + "/" + po.getId());
        deptMapper.updateById(po);
        return toVO(po);
    }

    @Override
    public DeptVO update(Long id, DeptUpdateReq req) {
        // 检查该部门是否为根部门
        if (id == 0) {
            throw new BizException("ROOT_DEPT_CANNOT_UPDATE");
        }
        // 检查该部门是否存在
        DeptPO po = deptMapper.selectById(id);
        if (po == null) {
            throw new BizException("DEPT_NOT_EXIST");
        }
        // 如果需要更新parent_id
        if (req.getParentId() != null) {
            // 如果该部门存在子部门，则不允许更新
            if (deptMapper.selectList(new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getParentId, id)) != null) {
                throw new BizException("CHILD_DEPT_EXIST");
            }
            // 如果要指向的父部门不存在，则不允许更新
            if (deptMapper.selectById(req.getParentId()) == null) {
                throw new BizException("PARENT_DEPT_NOT_EXIST");
            }
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
        // 检查该部门是否为根部门
        if (id == 0) {
            throw new BizException("ROOT_DEPT_CANNOT_DELETE");
        }
        // 检查该部门的子部门是否存在
        if (deptMapper.selectList(new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getParentId, id)) != null) {
            throw new BizException("CHILD_DEPT_EXIST");
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
    public List<DeptTreeVO> getDeptTree() {
        // 查询所有部门
        List<DeptPO> deptList = deptMapper.selectList(new LambdaQueryWrapper<DeptPO>().ne(DeptPO::getParentId, (long) -1));
        List<DeptTreeVO> deptTreeList = deptList.stream().map(this::toDeptTreeVO).toList();
        // 构建映射表
        Map<Long, DeptTreeVO> departmentMap = deptTreeList.stream()
                .collect(Collectors.toMap(DeptTreeVO::getId, Function.identity()));
        // 遍历所有部门，构建部门树
        List<DeptTreeVO> rootDepartments = new ArrayList<>();
        for (DeptTreeVO deptTree : deptTreeList) {
            if (deptTree.getParentId().equals(0L)) {
                rootDepartments.add(deptTree);
            } else {
                DeptTreeVO parentDeptTree = departmentMap.get(deptTree.getParentId());
                if (parentDeptTree == null) {
                    throw new BizException("PARENT_DEPT_NOT_EXIST");
                }
                addChild(parentDeptTree, deptTree);
            }
        }
        return rootDepartments;
    }

    @Override
    public DeptTreeVO getDeptTree(Long id) {
        // 检查部门是否存在
        DeptPO dept = deptMapper.selectById(id);
        if (dept == null) {
            return null;
        }
        DeptTreeVO rootDept = toDeptTreeVO(dept);
        // 根据path获取当前部门下的所有部门
        List<DeptPO> deptList = deptMapper.selectList(new LambdaQueryWrapper<DeptPO>()
                .likeRight(DeptPO::getPath, dept.getPath() + "/%"));
        List<DeptTreeVO> deptTreeList = deptList.stream().map(this::toDeptTreeVO).toList();
        // 构建映射表
        Map<Long, DeptTreeVO> departmentMap = deptTreeList.stream().collect(Collectors.toMap(DeptTreeVO::getId, Function.identity()));
        // 遍历deptTreeList，构建部门树
        List<DeptTreeVO> secondDepartments = new ArrayList<>();
        for (DeptTreeVO deptTree : deptTreeList) {
            if (deptTree.getParentId().equals(id)) {
                secondDepartments.add(deptTree);
            } else {
                DeptTreeVO parentDeptTree = departmentMap.get(deptTree.getParentId());
                if (parentDeptTree == null) {
                    throw new BizException("PARENT_DEPT_NOT_EXIST");
                }
                addChild(parentDeptTree, deptTree);
            }
        }
        for (DeptTreeVO deptTree : secondDepartments) {
            addChild(rootDept, deptTree);
        }
        return rootDept;
    }

    @Override
    public void moveDept(Long id, Long targetDeptId) {
        // TODO: 待实现
    }
}
