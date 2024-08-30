package com.luckybird.springbackend.api.vo;

import com.luckybird.springbackend.common.constant.StatusEnum;
import lombok.Data;

import java.util.List;

/**
 * 用户信息VO
 *
 * @author 新云鸟
 */
@Data
public class UserVO {

    /**
     * ID
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
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 组织ID
     */
    private Long organizationId;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 职位
     */
    private List<String> occupation;

    /**
     * 备注
     */
    private String remark;
}
