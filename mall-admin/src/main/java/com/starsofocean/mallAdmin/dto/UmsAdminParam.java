package com.starsofocean.mallAdmin.dto;

import lombok.Data;

@Data
public class UmsAdminParam {

    private String username;

    private String password;

    private String icon;

//    @Email
    private String email;

    private String nickName;

    private String note;
}
