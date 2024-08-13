package com.luckybird.springbackend.exception;

import com.luckybird.springbackend.exception.error.ErrorInfoEnum;

/**
 * 业务异常
 *
 * @author 新云鸟
 */
public class BizException extends RuntimeException {
    protected final ErrorInfoEnum errorInfoEnum;
    public BizException(String message) {
        super(message);
        this.errorInfoEnum = ErrorInfoEnum.getInfoByMessage(message);
    }
}
