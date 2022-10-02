package com.starsofocean.mallAdmin.dto;

import lombok.Data;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/9/22 22:34
 */

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;
}
