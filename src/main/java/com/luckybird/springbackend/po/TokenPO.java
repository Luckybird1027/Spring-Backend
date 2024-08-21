package com.luckybird.springbackend.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token实体
 *
 * @author 新云鸟
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenPO {
    // TODO: 最后实现踢人需求
    /**
     * token凭据
     */
    private String accessToken;

    /**
     * 用户id
     */
    private Long userId;

}
