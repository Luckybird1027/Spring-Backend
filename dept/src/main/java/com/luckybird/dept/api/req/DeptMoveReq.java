package com.luckybird.dept.api.req;

import lombok.Data;

/**
 * 部门移动请求
 *
 * @author 新云鸟
 */
@Data
public class DeptMoveReq {
    /**
     * 目标部门ID
     */
    private Long targetDeptId;
}
