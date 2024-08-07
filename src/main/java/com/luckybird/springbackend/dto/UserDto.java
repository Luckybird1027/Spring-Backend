package com.luckybird.springbackend.dto;

import com.luckybird.springbackend.exception.ExceptionMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 新云鸟
 */
@Setter
@Getter
public class UserDto {
    @NotBlank(message = ExceptionMessages.USERNAME_IS_EMPTY)
    private String username;
    @NotBlank(message = ExceptionMessages.PASSWORD_IS_EMPTY)
    private String password;

    public UserDto() {
    }

}
