package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.exception.ExceptionMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
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
