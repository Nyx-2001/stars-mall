package com.starsofocean.mallCommon.exception;

import com.starsofocean.mallCommon.api.IErrorCode;

/**
 * @author starsofocean
 * date 2022/9/22 13:33
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
