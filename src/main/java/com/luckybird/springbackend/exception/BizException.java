package com.luckybird.springbackend.exception;

import com.luckybird.springbackend.common.constant.ErrorInfoEnum;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author 新云鸟
 */
@Getter
public class BizException extends RuntimeException {
    protected final ErrorInfoEnum errorInfoEnum;

    public BizException(ErrorInfoEnum errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfoEnum = errorInfo;
    }
}
