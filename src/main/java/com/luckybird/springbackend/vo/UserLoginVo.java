package com.luckybird.springbackend.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 新云鸟
 */
@Getter
@Setter
public class UserLoginVo {
    private Long id;
    private String username;

    public UserLoginVo() {
    }
}
