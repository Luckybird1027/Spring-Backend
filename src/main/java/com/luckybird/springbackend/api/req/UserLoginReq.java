package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.exception.ExceptionMessages;
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
    @NotBlank(message = ExceptionMessages.ACCOUNT_IS_EMPTY)
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = ExceptionMessages.PASSWORD_IS_EMPTY)
    private String password;
}
