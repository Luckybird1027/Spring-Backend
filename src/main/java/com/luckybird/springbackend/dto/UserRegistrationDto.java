package com.luckybird.springbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 新云鸟
 */
@Setter
@Getter
public class UserRegistrationDto {
    @NotBlank(message = "The username cannot be empty")
    private String username;
    @NotBlank(message = "The password cannot be empty")
    private String password;

    public UserRegistrationDto() {
    }

}
