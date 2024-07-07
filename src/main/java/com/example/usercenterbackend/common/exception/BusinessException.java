package com.example.usercenterbackend.common.exception;

import com.example.usercenterbackend.common.CommonErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常类，多加个descrip
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private int code;

    private String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(CommonErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getErrorCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(CommonErrorCode errorCode, String description) {
        super(errorCode.getMsg());
        this.code = errorCode.getErrorCode();
        this.description = description;
    }
}
