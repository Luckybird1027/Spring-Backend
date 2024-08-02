package com.luckybird.backendapp.exception;

/**
 * @author 新云鸟
 */
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}
