package com.luckybird.springbackend.exception.error;

import lombok.Getter;

/**
 * @author 新云鸟
 */
@Getter
public enum ErrorInfoEnum implements ErrorInfo {
    // TODO: 抽离通用代码，将具体错误码放到子类中

    USERNAME_ALREADY_EXISTS("00001", "username already exists"),
    USER_NOT_EXIST("00002", "user not exist"),
    USERNAME_IS_EMPTY("00003", "username is empty"),
    PASSWORD_IS_EMPTY("00004", "password is empty"),
    INCORRECT_PASSWORD("00005", "incorrect password");


    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    ErrorInfoEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorInfoEnum getByCode(String code) {
        for (ErrorInfoEnum errorInfo : ErrorInfoEnum.values()) {
            if (errorInfo.code.equals(code)) {
                return errorInfo;
            }
        }
        return null;
    }
}
