package com.luckybird.springbackend.api.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 新云鸟
 */
@Data
public class UserChangePasswordReq {

    /**
     * 旧密码
     */
    @NotBlank(message = "PASSWORD_IS_EMPTY")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "PASSWORD_IS_EMPTY")
    private String newPassword;
}
