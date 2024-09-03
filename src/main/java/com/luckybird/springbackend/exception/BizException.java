package com.luckybird.springbackend.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author 新云鸟
 */
@Getter
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }
}
