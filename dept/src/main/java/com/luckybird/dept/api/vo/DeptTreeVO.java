package com.luckybird.dept.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 部门树形结构VO
 *
 * @author 新云鸟
 */
@Data
public class DeptTreeVO {

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
     * 子部门列表
     */
    private List<DeptTreeVO> children;

}
