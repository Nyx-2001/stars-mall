package com.starsofocean.mallAdmin.dto;

import com.starsofocean.mallAdmin.domain.UmsMenu;
import lombok.Data;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/1 19:34
 */
@Data
public class UmsMenuNode extends UmsMenu {

    private List<UmsMenuNode> children;
}
