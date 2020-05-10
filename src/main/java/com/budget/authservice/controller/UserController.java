package com.budget.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/")
    public String index() {
        return "{not found}";
    }

}

//    private final ILoginServiceIOClient loginServiceIOClient;
//
//    @Autowired
//    public UserController(ILoginServiceIOClient loginServiceIOClient) {
//        this.loginServiceIOClient = loginServiceIOClient;
//    }



//    @RequestMapping(method = RequestMethod.POST, value = "/login")
//    public ResponseEntity login(@RequestParam("username") final String username,
//                                @RequestParam("password") final String password,
//                                @RequestParam("grant_type") final String grantType) {
//        return ResponseEntity.ok(loginServiceIOClient.login(username,password,grantType).getBody());
//    }


