package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.converter.OccupationConverter;
import com.luckybird.springbackend.exception.ExceptionMessages;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 用户注册请求
 *
 * @author 新云鸟
 */
@Data
public class UserRegisterReq {

    /**
     * 账号
     */
    @NotBlank(message = ExceptionMessages.USERNAME_IS_EMPTY)
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
    private Byte status;

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
    @Convert(converter = OccupationConverter.class)
    private List<String> occupation;

    /**
     * 备注
     */
    private String remark;
}
