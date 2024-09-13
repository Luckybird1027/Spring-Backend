package com.luckybird.repository.constant;

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
public enum UserStatusEnum {

    NORMAL(0, "正常"),
    DISABLE(1, "禁用");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

    private static final Map<Integer, UserStatusEnum> MAP = Arrays.stream(UserStatusEnum.values()).collect(Collectors.toMap(UserStatusEnum::getKey, e -> e));

    public static UserStatusEnum of(Integer key) {
        return MAP.get(key);
    }
}