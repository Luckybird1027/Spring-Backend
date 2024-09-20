package com.luckybird.log.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型枚举
 *
 * @author 新云鸟
 */
@Getter
@AllArgsConstructor
public enum OperateTypeEnum {

    CREATE("create"),
    DELETE("delete"),
    UPDATE("update");

    private final String value;

}
