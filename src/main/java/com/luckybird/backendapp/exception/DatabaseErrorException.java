package com.luckybird.backendapp.exception;

/**
 * @author 新云鸟
 */
public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException() {
        super(ExceptionMessages.DATABASE_ERROR);
    }
}
