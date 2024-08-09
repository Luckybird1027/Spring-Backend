package com.luckybird.springbackend.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 新云鸟
 */
@Getter
@Setter
public class UserVO {
    private Long id;
    private String username;

    public UserVO() {
    }
}
