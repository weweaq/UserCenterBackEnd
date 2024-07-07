package com.example.usercenterbackend.common;

import lombok.Getter;

@Getter
public enum CommonErrorCode {
    PARAMS_ERROR(400001, "参数错误", ""),
    NO_PERMISSION(400101, "无权限", ""),
    INSERT_FAILED(500001, "插入失败", ""),
    NO_AUTH(400201, "未认证", "");

    private final int errorCode;
    private final String msg;
    private final String description;

    CommonErrorCode(int errorCode, String msg, String description) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.description = description;
    }
}
