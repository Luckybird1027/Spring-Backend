package com.luckybird.dept.api.vo;

import lombok.Data;

/**
 * 部门信息VO
 *
 * @author 新云鸟
 */
@Data
public class DeptVO {

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
     * 部门路径
     */
    private String path;

    /**
     * 备注
     */
    private String remark;

}
