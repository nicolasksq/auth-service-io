package com.budget.io.authserviceio.rest;

//import com.budget.io.authserviceio.rest.pojo.ErrorIO;
//import com.budget.io.authserviceio.rest.pojo.TokenResponse;
//import feign.FeignException;
//
//import java.util.Map;
//
//public class LoginServiceIOFallback implements ILoginServiceIOClient{
//
//    private final Throwable cause;
//
//    public LoginServiceIOFallback(Throwable cause) {
//        this.cause = cause;
//    }
//
//    @Override
//    public TokenResponse login( Map<String, ?> loginRequest) {
//        if (cause instanceof FeignException && ((FeignException) cause).status() != 200) {
//            // Treat the HTTP 404 status
//            print
//        }
//        return new TokenResponse.TokenResponseBuilder();
//    }
//}

