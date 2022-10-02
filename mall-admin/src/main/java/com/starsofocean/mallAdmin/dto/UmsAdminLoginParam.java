package com.starsofocean.mallAdmin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {

    private String username;

    private String password;

}
