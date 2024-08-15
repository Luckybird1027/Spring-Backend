package com.luckybird.springbackend.exception.error;

import lombok.Getter;
import lombok.ToString;

/**
 * 错误码枚举
 *
 * @author 新云鸟
 */
@Getter
@ToString
public enum ErrorInfoEnum{

    /**
     * 用户相关错误码
     */
    ACCOUNT_ALREADY_EXISTS("00001", "account already exists"),
    USER_NOT_EXIST("00002", "user not exist"),
    USERNAME_IS_EMPTY("00003", "username is empty"),
    PASSWORD_IS_EMPTY("00004", "password is empty"),
    INCORRECT_PASSWORD("00005", "incorrect password"),
    INCORRECT_USERNAME_OR_PASSWORD("00006", "incorrect username or password"),
    INCORRECT_OLD_PASSWORD("00007", "incorrect old password"),


    /**
     * 其他错误码
     */
    INTERNAL_SERVER_ERROR("99999", "internal server error");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    public final String message;

    ErrorInfoEnum(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorInfoEnum getInfoByCode(String code) {
        for (ErrorInfoEnum errorInfo : ErrorInfoEnum.values()) {
            if (errorInfo.code.equals(code)) {
                return errorInfo;
            }
        }
        return null;
    }

    public static ErrorInfoEnum getInfoByMessage(String message) {
        for (ErrorInfoEnum errorInfo : ErrorInfoEnum.values()) {
            if (errorInfo.message.equals(message)) {
                return errorInfo;
            }
        }
        return null;
    }
}