package com.budget.io.authserviceio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableRedisRepositories
public class AuthServiceIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceIoApplication.class, args);
    }

}
