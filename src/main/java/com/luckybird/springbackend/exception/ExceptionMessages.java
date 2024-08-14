package com.luckybird.springbackend.exception;

/**
 * 异常信息
 *
 * @author 新云鸟
 */
public final class ExceptionMessages {
    private ExceptionMessages() {
    }

    public static final String USERNAME_ALREADY_EXISTS = "username already exists";
    public static final String USER_NOT_EXIST = "user not exist";
    public static final String USERNAME_IS_EMPTY = "username is empty";
    public static final String PASSWORD_IS_EMPTY = "password is empty";
//    public static final String INCORRECT_PASSWORD = "incorrect password";
    public static final String INCORRECT_USERNAME_OR_PASSWORD = "incorrect username or password";
}
