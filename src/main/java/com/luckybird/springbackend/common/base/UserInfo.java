package com.luckybird.springbackend.common.base;

import lombok.Data;

/**
 * 用户信息（用于传递上下文信息）
 *
 * @author 新云鸟
 */
@Data
public class UserInfo {
    // TODO: 目前只需要使用到ID，后续可能需要增加更多信息
    /**
     * 用户ID
     */
    private Long id;

    public boolean isEmpty() {
        return id == null;
    }
}