package com.starsofocean.mallAdmin.dto;

import lombok.Data;

/**
 * @author starsofocean
 * date 2022/10/12 13:55
 */
@Data
public class UpdateAdminPasswordParam {

    private String username;

    private String oldPassword;

    private String newPassword;
}
