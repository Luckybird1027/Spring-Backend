package com.luckybird.springbackend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 错误码枚举
 *
 * @author 新云鸟
 */
@Getter
@ToString
@AllArgsConstructor
public enum ErrorInfoEnum {

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
    ACCOUNT_IS_EMPTY("00008", "account is empty"),
    USER_ALREADY_LOGIN("00009", "user already login"),
    USER_DISABLED("00010", "user disabled"),
    UNAUTHORIZED_ACCESS("00011", "unauthorized access"),
    USER_NOT_LOGIN("00012", "user not login"),
    INVALID_PARAMETER("00013", "invalid parameter"),


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

    public static ErrorInfoEnum getInfoByMessage(String message) {
        for (ErrorInfoEnum errorInfo : ErrorInfoEnum.values()) {
            if (errorInfo.message.equals(message)) {
                return errorInfo;
            }
        }
        return null;
    }
}