package com.starsofocean.mallDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author starsofocean
 * date 2022/9/28 12:38
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MallDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallDemoApplication.class,args);
    }
}
