package com.budget.io.authserviceio.controller;

import com.budget.io.authserviceio.controller.pojo.LoginRequest;
import com.budget.io.authserviceio.controller.pojo.LoginResponse;
import com.budget.io.authserviceio.controller.pojo.RefreshTokenRequest;
import com.budget.io.authserviceio.model.Session;
import com.budget.io.authserviceio.model.User;
import com.budget.io.authserviceio.rest.ILoginServiceIOClient;
import com.budget.io.authserviceio.rest.pojo.ErrorIO;
import com.budget.io.authserviceio.rest.pojo.TokenResponse;
import com.budget.io.authserviceio.service.SessionService;
import com.budget.io.authserviceio.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
        return ResponseEntity.ok("Hola Mundo / Hello World");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest, @RequestParam(required = false) String session) {
        Optional<Session> cacheSession = sessionService.findBySession(session);
        cacheSession.ifPresent(sessionFound -> {
            try {
                ObjectMapper oMapper = new ObjectMapper();
                RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(sessionFound.getRefreshToken());
                Optional<TokenResponse> tokenResponse = loginServiceIOClient.login(oMapper.convertValue(refreshTokenRequest, Map.class));

                if (tokenResponse.isPresent()) {
                    User userDb = userService.getByUsername(loginRequest.getUsername())
                            .orElseGet(() -> this.createUser(loginRequest.getUsername()));
                    sessionService.save(new Session(
                            userDb.getId(),
                            tokenResponse.get().getRefreshToken(),
                            tokenResponse.get().getAccessToken(),
                            sessionFound.getSession()
                    ));
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        });
        Session newSession = cacheSession.orElseGet(() -> {
            try {
                ObjectMapper oMapper = new ObjectMapper();
                Optional<TokenResponse> tokenResponse = loginServiceIOClient.login(oMapper.convertValue(loginRequest, Map.class));
                if (tokenResponse.isPresent()) {
                    User userDb = userService.getByUsername(loginRequest.getUsername())
                            .orElseGet(() -> this.createUser(loginRequest.getUsername()));
                    return sessionService.save(new Session(
                            userDb.getId(),
                            tokenResponse.get().getRefreshToken(),
                            tokenResponse.get().getAccessToken(),
                            null
                    )).orElse(null);
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            return null;
        });
        if (cacheSession.isPresent()) {
            return ResponseEntity.ok(new LoginResponse(cacheSession.get().getUid(), cacheSession.get().getSession(), true));
        }
        if (newSession != null) {
            return ResponseEntity.ok(new LoginResponse(newSession.getUid(), newSession.getSession(), false));
        }

//        TODO check how to trigger a real exception with its error.
        ErrorIO error = new ErrorIO(999, "Something went wrong");
        return ResponseEntity.badRequest().body(error.getMessage());
    }

    /**
     * @param username
     * @return
     */
    private User createUser(String username) {
        User userEntity = new User();
        userEntity.setUsername(username);
        userService.save(userEntity);
        return userEntity;
    }

}
