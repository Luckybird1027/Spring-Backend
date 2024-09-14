package com.luckybird.dept.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luckybird.common.base.Difference;
import com.luckybird.common.base.PageResult;
import com.luckybird.common.context.utils.ContextUtils;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.i18n.utils.StringResourceUtils;
import com.luckybird.common.json.utils.JsonUtils;
import com.luckybird.dept.api.req.DeptCreateReq;
import com.luckybird.dept.api.req.DeptMoveReq;
import com.luckybird.dept.api.req.DeptQueryReq;
import com.luckybird.dept.api.req.DeptUpdateReq;
import com.luckybird.dept.api.vo.DeptTreeVO;
import com.luckybird.dept.api.vo.DeptVO;
import com.luckybird.log.constant.OperateTypeEnum;
import com.luckybird.log.utils.LogUtils;
import com.luckybird.repository.dept.DeptMapper;
import com.luckybird.repository.dept.DeptPO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final String CURRENT_MODULE = StringResourceUtils.format("DEPT");

    private DeptVO toVo(DeptPO po) {
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

    private DeptPO toPo(DeptCreateReq req) {
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

    private DeptTreeVO buildRootDeptTree(Long id, List<DeptPO> childDeptList, DeptTreeVO rootDept) {
        List<DeptTreeVO> deptTreeList = childDeptList.stream().map(this::toDeptTreeVO).toList();
        // 构建映射表
        Map<Long, DeptTreeVO> departmentMap = deptTreeList.stream().collect(Collectors.toMap(DeptTreeVO::getId, Function.identity()));
        departmentMap.put(id, rootDept);
        // 构建子一级部门的部门树
        List<DeptTreeVO> secondDepartments = buildSecondDepartments(id, deptTreeList, departmentMap);
        // 构建当前根部门的部门树
        for (DeptTreeVO deptTree : secondDepartments) {
            addChild(rootDept, deptTree);
        }
        return rootDept;
    }

    private List<DeptTreeVO> buildSecondDepartments(Long id, List<DeptTreeVO> deptTreeList, Map<Long, DeptTreeVO> departmentMap) {
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
        return secondDepartments;
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

    @Async
    protected void differenceLog(DeptPO oldPo, DeptPO newPo, String feature) {
        if (oldPo == null || newPo == null) {
            return;
        }
        List<Difference> differences = new ArrayList<>();
        if (!oldPo.getName().equals(newPo.getName())) {
            differences.add(new Difference("name", oldPo.getName(), newPo.getName()));
        }
        if (!oldPo.getParentId().equals(newPo.getParentId())) {
            differences.add(new Difference("parentId", oldPo.getParentId(), newPo.getParentId()));
        }
        if (!oldPo.getRemark().equals(newPo.getRemark())) {
            differences.add(new Difference("remark", oldPo.getRemark(), newPo.getRemark()));
        }
        if (!differences.isEmpty()) {
            LogUtils.log(CURRENT_MODULE, OperateTypeEnum.UPDATE.getValue(), feature, differences);
        }
    }

    @Async
    protected void briefLog(DeptVO vo, String type, String feature) {
        LogUtils.log(CURRENT_MODULE, type, feature, vo);
    }

    @Override
    public DeptVO get(Long id) {
        DeptPO po = deptMapper.selectById(id);
        if (po == null) {
            return new DeptVO();
        }
        return toVo(po);
    }

    @Override
    public List<DeptVO> batchGet(Set<Long> ids) {
        return deptMapper.selectBatchIds(ids).stream().map(this::toVo).toList();
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
        DeptPO po = toPo(req);
        po.setPath(parentDept.getPath() + "/" + parentDept.getId());
        po.setCreatorId(ContextUtils.getUserInfo().getId());
        po.setCreateTime(LocalDateTime.now());
        deptMapper.insert(po);
        briefLog(toVo(po), OperateTypeEnum.CREATE.getValue(), "dept.create");
        return toVo(po);
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
        DeptPO oldPo = JsonUtils.deepClone(po);
        DeptPO newPo = updateByReq(po, req);
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
            // 更新path
            newPo.setPath(deptMapper.selectById(req.getParentId()).getPath() + "/" + req.getParentId());
        }
        // 更新其他部门信息
        newPo.setId(id);
        newPo.setUpdaterId(ContextUtils.getUserInfo().getId());
        newPo.setUpdateTime(LocalDateTime.now());
        deptMapper.updateById(newPo);
        differenceLog(oldPo, newPo, "dept.update");
        return toVo(newPo);
    }

    @Override
    public void delete(Long id) {
        // 检查该部门是否为根部门
        if (id == 0) {
            return;
        }
        DeptPO po = deptMapper.selectById(id);
        if (po == null) {
            return;
        }
        // 检查该部门的子部门是否存在
        if (deptMapper.selectList(new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getParentId, id)) != null) {
            throw new BizException("CHILD_DEPT_EXIST");
        }
        // 删除部门
        deptMapper.deleteById(id);
        briefLog(toVo(po), OperateTypeEnum.DELETE.getValue(), "dept.delete");
    }

    @Override
    public List<DeptVO> list(DeptQueryReq req) {
        List<DeptPO> poList = deptMapper.selectList(wrapperByReq(req));
        return poList.stream().map(this::toVo).toList();
    }

    @Override
    public PageResult<DeptVO> page(DeptQueryReq req, Long current, Long pageSize, boolean searchCount) {
        IPage<DeptPO> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<DeptPO> wrapper = wrapperByReq(req);
        IPage<DeptPO> deptPage = deptMapper.selectPage(page, wrapper);
        List<DeptPO> poList = deptPage.getRecords();
        List<DeptVO> voList = poList.stream().map(this::toVo).toList();
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
        List<DeptPO> childDeptList = deptMapper.selectList(new LambdaQueryWrapper<DeptPO>()
                .likeRight(DeptPO::getPath, dept.getPath() + "/" + dept.getId()));
        return buildRootDeptTree(id, childDeptList, rootDept);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DeptTreeVO moveDept(Long id, DeptMoveReq req) {
        Long targetDeptId = req.getTargetDeptId();
        DeptPO dept = deptMapper.selectById(id);
        DeptPO targetDept = deptMapper.selectById(targetDeptId);
        // 检查当前部门和目标部门是否存在
        if (dept == null || targetDept == null) {
            throw new BizException("DEPT_NOT_EXIST");
        }
        DeptPO oldDept = JsonUtils.deepClone(dept);
        // 检查当前部门是否有子部门
        if (deptMapper.selectList(new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getParentId, id)) == null) {
            // 若不存在子部门，则直接移动当前部门即可
            dept.setParentId(targetDeptId);
            dept.setPath(targetDept.getPath() + "/" + targetDept.getId());
            dept.setUpdaterId(ContextUtils.getUserInfo().getId());
            dept.setUpdateTime(LocalDateTime.now());
            deptMapper.updateById(dept);
            differenceLog(oldDept, dept, "dept.move");
            return toDeptTreeVO(dept);
        }

        // 若当前部门存在子部门，则需移动当前部门及以下所有部门
        String deptPath = dept.getPath();
        // 1. 移动当前部门
        dept.setParentId(targetDeptId);
        dept.setPath(targetDept.getPath() + "/" + targetDept.getId());
        dept.setUpdaterId(ContextUtils.getUserInfo().getId());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateById(dept);
        DeptTreeVO rootDept = toDeptTreeVO(dept);

        // 2. 移动子部门及以下所有部门
        // 将所有开头为当前部门path的部门的前面path部门替换为目标部门的path+目标部门的id
        // 比如当前部门的id为2，当前部门的path为/0/1，目标部门的id为13，目标部门的path为/0/1/5，
        // 当前的子部门及以下部门的其中一个部门的path为/0/1/2/21，
        // 则需要将其替换成/0/1/5/13/2/21

        // 先查询所有子部门及以下所有部门
        List<DeptPO> childDeptList = deptMapper.selectList(new LambdaQueryWrapper<DeptPO>()
                .likeRight(DeptPO::getPath, deptPath + "/" + id));
        // 遍历子部门及以下所有部门，更新path
        for (DeptPO childDept : childDeptList) {
            childDept.setPath(targetDept.getPath() + "/" + targetDept.getId() + childDept.getPath().substring(deptPath.length()));
            childDept.setUpdaterId(ContextUtils.getUserInfo().getId());
            childDept.setUpdateTime(LocalDateTime.now());
            deptMapper.updateById(childDept);
        }
        differenceLog(oldDept, dept, "dept.move");
        return buildRootDeptTree(id, childDeptList, rootDept);
    }
}
