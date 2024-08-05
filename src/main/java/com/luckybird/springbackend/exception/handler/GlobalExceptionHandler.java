package com.luckybird.springbackend.exception.handler;

import com.luckybird.springbackend.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author 新云鸟
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException e, HttpServletRequest request) {
        String failMsg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("BindException: " + failMsg + ", URL: " + request.getRequestURI());
        return new ResponseEntity<>(failMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<String> handleBizException(BizException e, HttpServletRequest request) {
        String failMsg = e.getMessage();
        log.error("BizException: " + failMsg + ", URL: " + request.getRequestURI());
        return new ResponseEntity<>(failMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleException(Throwable e, HttpServletRequest request) {
        log.error("Exception: " + e.getMessage() + ", URL: " + request.getRequestURI());
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
