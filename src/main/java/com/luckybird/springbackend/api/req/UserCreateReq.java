package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.exception.ExceptionMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 用户创建请求
 *
 * @author 新云鸟
 */
@Data
public class UserCreateReq {

    /**
     * 账号
     */
    @NotBlank(message = ExceptionMessages.ACCOUNT_IS_EMPTY)
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = ExceptionMessages.PASSWORD_IS_EMPTY)
    private String password;

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
    private Integer status;

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
