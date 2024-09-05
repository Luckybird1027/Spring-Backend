package com.luckybird.dept.base;

import lombok.Data;

import java.util.List;

/**
 * 部门树形查询辅助类
 *
 * @author 新云鸟
 */
@Data
public class DeptTreeInfo {

    /**
     * ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子部门ID列表
     */
    private List<Long> childIds;

    /**
     * 子部门列表
     */
    private List<DeptTreeInfo> children;

}
