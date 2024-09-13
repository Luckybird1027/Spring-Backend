package com.luckybird.logutil.constant;

import com.luckybird.common.utils.StringResourceUtils;
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

    CREATE(StringResourceUtils.format("CREATE")),
    DELETE(StringResourceUtils.format("DELETE")),
    UPDATE(StringResourceUtils.format("UPDATE")),
    QUERY(StringResourceUtils.format("QUERY"));

    private final String value;

}
