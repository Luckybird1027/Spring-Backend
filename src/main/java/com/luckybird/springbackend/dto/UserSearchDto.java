package com.luckybird.springbackend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 新云鸟
 */
@Getter
@Setter
public class UserSearchDto {
    private String keyword;

    UserSearchDto() {
    }
}
