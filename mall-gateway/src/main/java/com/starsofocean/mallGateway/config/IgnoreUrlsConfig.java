package com.starsofocean.mallGateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/9/27 16:52
 */
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
