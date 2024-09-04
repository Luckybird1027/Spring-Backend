package com.luckybird.user.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户状态枚举类
 *
 * @author 新云鸟
 */

@Getter
@AllArgsConstructor
public enum StatusEnum {

    NORMAL(0, "正常"),
    DISABLE(1, "禁用");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

    private static final Map<Integer, StatusEnum> MAP = Arrays.stream(StatusEnum.values()).collect(Collectors.toMap(StatusEnum::getKey, e -> e));

    public static StatusEnum of(Integer key) {
        return MAP.get(key);
    }
}