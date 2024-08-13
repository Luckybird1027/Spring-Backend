package com.luckybird.springbackend.exception.error;

import lombok.Data;

/**
 * @author 新云鸟
 */

@Data
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

    public ErrorResult(String code, String message){
        this.code = code;
        this.message = message;
    }
}
