package com.luckybird.springbackend.common.base;

import lombok.Data;

import java.util.List;

/**
 * 用户信息（用于传递上下文信息）
 *
 * @author 新云鸟
 */
@Data
public class UserInfo {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String username;

    /**
     * 组织ID
     */
    private Long organizationId;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 权限角色
     */
    private List<String> permissionRole;

    /**
     * 用户权限
     */
    private List<String> permission;
}
