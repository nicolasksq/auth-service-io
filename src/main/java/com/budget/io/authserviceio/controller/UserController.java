package com.budget.io.authserviceio.controller;

import com.budget.io.authserviceio.controller.pojo.LoginRequest;
import com.budget.io.authserviceio.controller.pojo.LoginResponse;
import com.budget.io.authserviceio.model.User;
import com.budget.io.authserviceio.repository.UserRepository;
import com.budget.io.authserviceio.rest.ILoginServiceIOClient;
import com.budget.io.authserviceio.rest.pojo.TokenResponse;
import com.budget.io.authserviceio.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private ILoginServiceIOClient loginServiceIOClient;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok("{FAIL}");
    }


    //llaman al login, me fijo si el user esta en redis SI esta devuelvo el access_token
    //llaman al login, me fijo si el user esta en redis si NO esta me fijo que este en la DB por username
             //SI ESTA llamo al login de IO con grant_type password
             //SI NO ESTA lo guardo y llamo al login de IO con grant_type password
    //
    @RequestMapping(method = RequestMethod.POST, value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE ,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

         try{

             User userExist =
                     userService.getByUsername(loginRequest.getUsername());

             if(userExist == null) {
                 User user = new User();
                 user.setUsername(loginRequest.getUsername());
                 userService.save(user);

//                 create session
             }

             ObjectMapper oMapper = new ObjectMapper();
             TokenResponse tokenResponse =
                     loginServiceIOClient.login(oMapper.convertValue(loginRequest, Map.class));



                if(tokenResponse.getExpiresIn() > 0) {

                }

            return ResponseEntity.ok(LoginResponse.builder());

        }catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
         }

    }

}
