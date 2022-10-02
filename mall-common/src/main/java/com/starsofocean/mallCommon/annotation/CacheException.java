package com.starsofocean.mallCommon.annotation;

import java.lang.annotation.*;

/**
 * @author starsofocean
 * date 2022/9/27 17:19
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}