package com.example.usercenterbackend.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CommonRsp<T> {

    /**
     * 0-成功，其余失败，默认为1
     */
    private int errorCode;

    private String msg;

    private String description;

    private T data;

    public CommonRsp(int errorCode, String msg, String description, T data) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.description = description;
        this.data = data;
    }

    public static <T> CommonRsp<T> success(T data) {
        return new CommonRsp<>(0, "成功", "", data);
    }

    public static <T> CommonRsp<T> success(String description, T data) {
        return new CommonRsp<>(0, "成功", description, data);
    }

    public static <T> CommonRsp<T> fail(T data) {
        return new CommonRsp<>(1, "失败", "", data);
    }

    public static <T> CommonRsp<T> fail(String description, T data) {
        return new CommonRsp<>(1, "失败", description, data);
    }

    public static <T> CommonRsp<T> fail(CommonErrorCode errorCode) {
        return new CommonRsp<>(errorCode.getErrorCode(), errorCode.getMsg(), errorCode.getDescription(), null);
    }

    public static <T> CommonRsp<T> fail(CommonErrorCode errorCode, String description) {
        return new CommonRsp<>(errorCode.getErrorCode(), errorCode.getMsg(), description, null);
    }

    @Override
    public String toString() {
        return "CommonRsp{" +
                "errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
