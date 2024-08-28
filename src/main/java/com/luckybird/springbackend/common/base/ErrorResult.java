package com.luckybird.springbackend.common.base;

import com.luckybird.springbackend.exception.error.ErrorInfoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误结果
 *
 * @author 新云鸟
 */

@Data
@NoArgsConstructor
public class ErrorResult {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public ErrorResult(ErrorInfoEnum errorInfoEnum){
        this.code = errorInfoEnum.getCode();
        this.message = errorInfoEnum.getMessage();
    }
}
