package com.luckybird.springbackend.api.vo;

import lombok.Data;

/**
 * 用户信息VO
 *
 * @author 新云鸟
 */
@Data
public class UserVO {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
}
