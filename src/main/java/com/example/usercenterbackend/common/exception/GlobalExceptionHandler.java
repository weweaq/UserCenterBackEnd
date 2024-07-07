package com.example.usercenterbackend.common.exception;

import com.example.usercenterbackend.common.CommonErrorCode;
import com.example.usercenterbackend.common.CommonRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonRsp BusinessExceptionHandler(BusinessException e) {
        log.error("BusinessExceptionHandler" + e.getMessage(), e);
        // TODO: 2024/7/7 再封装一层fail来做
        return new CommonRsp(e.getCode(), e.getMessage(), e.getDescription(), null);
    }


    @ExceptionHandler(RuntimeException.class)
    public CommonRsp RuntimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeExceptionHandler", e);
        return CommonRsp.fail(CommonErrorCode.INSERT_FAILED);
    }
}
