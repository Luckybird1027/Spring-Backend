package com.luckybird.backendapp.dto;

import com.luckybird.backendapp.exception.ExceptionMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 新云鸟
 */
@Setter
@Getter
public class UserLoginDto {
    @NotBlank(message = ExceptionMessages.USERNAME_IS_EMPTY)
    private String username;
    @NotBlank(message = ExceptionMessages.PASSWORD_IS_EMPTY)
    private String password;

    public UserLoginDto() {
    }

}
