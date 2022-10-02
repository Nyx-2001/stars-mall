package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/9/22 23:21
 */
@Data
public class UmsAdminRoleRelation implements Serializable {
    private Long id;
    private Long adminId;
    private Long roleId;
}
