package com.luckybird.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 键值对对象
 *
 * @author 新云鸟
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyValue {

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object value;

}
