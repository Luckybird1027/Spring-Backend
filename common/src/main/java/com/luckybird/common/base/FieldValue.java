package com.luckybird.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段值对象
 *
 * @author 新云鸟
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldValue {

    /**
     * 键
     */
    private String field;

    /**
     * 值
     */
    private Object value;

}
