package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/10/12 17:18
 */
@Data
public class UmsRoleResourceRelation implements Serializable {
    private Long id;
    private Long roleId;
    private Long resourceId;
}
