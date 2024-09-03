package com.luckybird.springbackend.api.req;

import lombok.Data;

import java.util.List;

/**
 * 用户更新请求
 *
 * @author 新云鸟
 */
@Data
public class UserUpdateReq {

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String username;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 组织ID
     */
    private Long organizationId;

    /**
     * 部门ID
     */
    private Long departmentId;

    // TODO：权限相关待定
//    /**
//     * 权限角色
//     */
//    private List<String> permissionRole;

    /**
     * 职位
     */
    private List<String> occupation;

    /**
     * 备注
     */
    private String remark;
}
