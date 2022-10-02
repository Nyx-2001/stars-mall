package com.starsofocean.mallAuth.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author starsofocean
 * date 2022/9/23 23:41
 */
@Data
@Builder
public class Oauth2TokenDto {

    private String token;

    private String refreshToken;

    private String tokenHead;

    private int expiresIn;
}
