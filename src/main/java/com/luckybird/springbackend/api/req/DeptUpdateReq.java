package com.luckybird.springbackend.api.req;

import lombok.Data;

/**
 * 部门创建请求
 *
 * @author 新云鸟
 */
@Data
public class DeptUpdateReq {

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

}
