package com.starsofocean.mallCommon.api;

import lombok.Data;

@Data
public class CR<T> {
    private long code;
    private String message;
    private T date;

    protected CR() {
    }

    protected CR(long code, String message, T date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CR<T> success(T data) {
        return new CR<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CR<T> success(T data, String message) {
        return new CR<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CR<T> failed(IErrorCode errorCode) {
        return new CR<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CR<T> failed(IErrorCode errorCode,String message) {
        return new CR<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CR<T> failed(String message) {
        return new CR<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CR<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CR<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CR<T> validateFailed(String message) {
        return new CR<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CR<T> unauthorized(T data) {
        return new CR<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CR<T> forbidden(T data) {
        return new CR<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

}
