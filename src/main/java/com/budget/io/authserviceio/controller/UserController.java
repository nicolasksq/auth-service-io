package com.budget.io.authserviceio.controller;

import com.budget.io.authserviceio.controller.pojo.LoginRequest;
import com.budget.io.authserviceio.controller.pojo.LoginResponse;
import com.budget.io.authserviceio.model.SessionJedis;
import com.budget.io.authserviceio.model.User;
import com.budget.io.authserviceio.rest.ILoginServiceIOClient;
import com.budget.io.authserviceio.rest.pojo.TokenResponse;
import com.budget.io.authserviceio.service.SessionService;
import com.budget.io.authserviceio.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.Session;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private ILoginServiceIOClient loginServiceIOClient;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok("{FAIL}");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE ,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest, @RequestParam(required = false) String session) {

         try {

             User user =
                     userService.getByUsername(loginRequest.getUsername());

             if (user == null) {
                 user = new User();
                 user.setUsername(loginRequest.getUsername());
                 userService.save(user);
             }

             //check if exist in redis
             Optional<SessionJedis> sessionJedis = sessionService.findBySession(session);

             if (!sessionJedis.isPresent()) {

                 ObjectMapper oMapper = new ObjectMapper();
                 TokenResponse tokenResponse =
                         loginServiceIOClient.login(oMapper.convertValue(loginRequest, Map.class));

                 sessionJedis = Optional.of(new SessionJedis(
                         user.getId(),
                         tokenResponse.getRefreshToken(),
                         null
                 ));

                 sessionService.save(sessionJedis.get());
             }

             LoginResponse response = new LoginResponse(sessionJedis.get().getUid(), sessionJedis.get().getSession());

             return ResponseEntity.ok(response);

         }catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
         }

    }

}
