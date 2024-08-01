package com.luckybird.backendapp.exception;

/**
 * @author 新云鸟
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super(ExceptionMessages.USERNAME_ALREADY_EXISTS);
    }
}
