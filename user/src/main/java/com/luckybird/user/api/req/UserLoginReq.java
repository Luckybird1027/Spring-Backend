package com.luckybird.user.api.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录请求
 *
 * @author 新云鸟
 */
@Data
public class UserLoginReq {

    /**
     * 账号
     */
    @NotBlank(message = "EMPTY_ACCOUNT")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "PASSWORD_IS_EMPTY")
    private String password;
}
