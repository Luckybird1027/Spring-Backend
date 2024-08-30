package com.luckybird.springbackend.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举类
 *
 * @author 新云鸟
 */

@Getter
@AllArgsConstructor
public enum StatusEnum {

    NORMAL((byte) 0, "正常"),
    DISABLE((byte) 1, "禁用");

    @EnumValue
    private final byte key;

    @JsonValue
    private final String display;

    public static StatusEnum getByKey(byte key) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getKey() == key) {
                return status;
            }
        }
        return null;
    }
}