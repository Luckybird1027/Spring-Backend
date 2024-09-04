package com.luckybird.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误结果
 *
 * @author 新云鸟
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResult {

    /**
     * 错误信息
     */
    private String message;

}
