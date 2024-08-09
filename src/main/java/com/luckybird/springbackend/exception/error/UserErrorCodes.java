package com.luckybird.springbackend.exception.error;

import lombok.Getter;

/**
 * @author 新云鸟
 */
@Getter
public enum UserErrorCodes implements ErrorCode {
    // TODO: 补充错误码信息
    USERNAME_ALREADY_EXISTS("00001","username already exists")
    ;
    private final String code;
    private final String message;

    UserErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }



}
