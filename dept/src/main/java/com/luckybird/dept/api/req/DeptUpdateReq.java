package com.luckybird.dept.api.req;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "EMPTY_DEPT_NAME")
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
