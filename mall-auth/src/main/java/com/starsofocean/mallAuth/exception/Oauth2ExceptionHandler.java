package com.starsofocean.mallAuth.exception;

import com.starsofocean.mallCommon.api.CR;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author starsofocean
 * date 2022/9/23 23:53
 */
@ControllerAdvice
public class Oauth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public CR handleOauth2(OAuth2Exception e) {
        return CR.failed(e.getMessage());
    }

}
