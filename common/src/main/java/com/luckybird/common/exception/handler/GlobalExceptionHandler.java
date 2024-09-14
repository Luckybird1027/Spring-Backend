package com.luckybird.common.exception.handler;

import com.luckybird.common.base.ErrorResult;
import com.luckybird.common.exception.BizException;
import com.luckybird.common.i18n.utils.StringResourceUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author 新云鸟
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleBindException(BindException e, HttpServletRequest request) {
        FieldError fieldError = e.getFieldErrors().isEmpty() ? null : e.getFieldErrors().getFirst();
        if (fieldError != null) {
            String message = fieldError.getDefaultMessage();
            ErrorResult errorResult = new ErrorResult(message, StringResourceUtils.format(message));
            log.error("BindException: " + errorResult + ", URL: " + request.getRequestURI());
            return errorResult;
        }
        return new ErrorResult("BIND_EXCEPTION", StringResourceUtils.format("BIND_EXCEPTION"));
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleBizException(BizException e, HttpServletRequest request) {
        ErrorResult errorResult = new ErrorResult(e.getCode(), e.getMessage());
        log.error("BizException: " + errorResult + ", URL: " + request.getRequestURI());
        return errorResult;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult handleException(Throwable e, HttpServletRequest request) {
        ErrorResult errorResult = new ErrorResult();
        if (e.getMessage() != null) {
            errorResult.setMessage(e.getMessage());
        } else {
            errorResult.setMessage(e.getCause().getMessage());
        }
        log.error("Exception: " + errorResult + ", URL: " + request.getRequestURI(), e);
        return new ErrorResult("INTERNAL_SERVER_ERROR", StringResourceUtils.format("INTERNAL_SERVER_ERROR"));
    }

}
