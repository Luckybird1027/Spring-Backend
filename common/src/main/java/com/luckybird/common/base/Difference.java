package com.luckybird.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 差异计算存储类
 *
 * @author 新云鸟
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Difference {
    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 旧值
     */
    private Object oldValue;

    /**
     * 新值
     */
    private Object newValue;

}
