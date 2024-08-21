package com.luckybird.springbackend.api.vo;

import lombok.Data;

/**
 * TokenVO
 *
 * @author 新云鸟
 */
@Data
public class TokenVO {
    // TODO: 最后实现踢人需求

    /**
     * token凭据
     */
    private String accessToken;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 过期时间
     */
    private Integer expireTime;
}
