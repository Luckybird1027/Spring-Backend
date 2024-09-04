package com.luckybird.common.exception;

import com.luckybird.common.utils.StringResourceUtils;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author 新云鸟
 */
@Getter
public class BizException extends RuntimeException {

    private String code;

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code) {
        super(StringResourceUtils.format(code));
        this.code = code;
    }
}
