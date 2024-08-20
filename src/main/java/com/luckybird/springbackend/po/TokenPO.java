package com.luckybird.springbackend.po;

import lombok.Data;

/**
 * Token实体
 *
 * @author 新云鸟
 */
@Data
public class TokenPO {
    // TODO: 需修改以实现踢人需求
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
