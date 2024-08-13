package com.luckybird.springbackend.exception.handler;

import com.luckybird.springbackend.exception.BizException;
import com.luckybird.springbackend.exception.error.ErrorInfoEnum;
import com.luckybird.springbackend.exception.error.ErrorResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.luckybird.springbackend.exception.error.ErrorInfoEnum.INTERNAL_SERVER_ERROR;

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
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        ErrorResult errorResult = new ErrorResult(Objects.requireNonNull(ErrorInfoEnum.getInfoByMessage(msg)));
        log.error("BindException: " + errorResult + ", URL: " + request.getRequestURI());
        return errorResult;
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleBizException(BizException e, HttpServletRequest request) {
        String msg = e.getMessage();
        ErrorResult errorResult = new ErrorResult(Objects.requireNonNull(ErrorInfoEnum.getInfoByMessage(msg)));
        log.error("BizException: " + errorResult + ", URL: " + request.getRequestURI());
        return errorResult;
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult handleException(Throwable e, HttpServletRequest request) {
        ErrorResult errorResult = new ErrorResult("99999", e.getMessage());
        log.error("Exception: " + errorResult + ", URL: " + request.getRequestURI());
        return new ErrorResult(INTERNAL_SERVER_ERROR);
    }

}
