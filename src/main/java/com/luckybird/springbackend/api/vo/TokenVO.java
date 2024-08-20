package com.luckybird.springbackend.api.vo;

import lombok.Data;

/**
 * TokenVO
 *
 * @author 新云鸟
 */
@Data
public class TokenVO {
    // TODO: 需修改以实现踢人需求
    /**
     * token凭据
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Integer expireTime;
}
