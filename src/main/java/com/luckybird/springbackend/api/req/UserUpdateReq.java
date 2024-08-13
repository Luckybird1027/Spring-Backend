package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.exception.ExceptionMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户更新请求
 *
 * @author 新云鸟
 */
@Data
public class UserUpdateReq {

    /**
     * 用户名
     */
    @NotBlank(message = ExceptionMessages.USERNAME_IS_EMPTY)
    private String username;
}
