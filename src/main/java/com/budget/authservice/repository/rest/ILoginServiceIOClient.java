//package com.budget.authservice.repository.rest;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Map;
//
//@FeignClient(name = "authservice-io")
//public interface ILoginServiceIOClient {
//    @PostMapping(name = "api.invertironline",
//            value = "https://api.invertironline.com/token",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<Map> login(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password,
//            @RequestParam("grant_type") String grantType
//    );
//}

