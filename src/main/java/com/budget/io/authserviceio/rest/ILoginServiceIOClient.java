package com.budget.io.authserviceio.rest;

import com.budget.io.authserviceio.config.FeignConfig;
import com.budget.io.authserviceio.rest.pojo.TokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@FeignClient(name = "authservice-io", url = "https://api.invertironline.com", configuration = FeignConfig.class)
public interface ILoginServiceIOClient {
    @PostMapping(name = "api.invertironline",
            value = "/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: " + MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Optional<TokenResponse> login(@RequestBody Map<String, ?> loginRequest);
}
