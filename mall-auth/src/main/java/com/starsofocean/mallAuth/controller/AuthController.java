package com.starsofocean.mallAuth.controller;

import com.starsofocean.mallAuth.domain.Oauth2TokenDto;
import com.starsofocean.mallCommon.api.CR;
import com.starsofocean.mallCommon.constant.AuthConstant;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author starsofocean
 * date 2022/9/23 23:56
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {
    @Resource
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/token")
    public CR<Oauth2TokenDto> postAccessToken(HttpServletRequest request,
                                              @RequestParam String grant_type,
                                              @RequestParam String client_id,
                                              @RequestParam String client_secret,
                                              @RequestParam(required = false) String refresh_token,
                                              @RequestParam(required = false) String username,
                                              @RequestParam(required = false) String password) throws HttpRequestMethodNotSupportedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type",grant_type);
        parameters.put("client_id",client_id);
        parameters.put("client_secret",client_secret);
        parameters.putIfAbsent("refresh_token",refresh_token);
        parameters.putIfAbsent("username",username);
        parameters.putIfAbsent("password",password);
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(request.getUserPrincipal(), parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();
        return CR.success(oauth2TokenDto);
    }
}
