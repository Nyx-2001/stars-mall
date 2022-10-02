package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/9/22 23:09
 */
@Data
public class UmsRoleMenuRelation implements Serializable {
    private Long id;
    private Long roleId;
    private Long menuId;
}
