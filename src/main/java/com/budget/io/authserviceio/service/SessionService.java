package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

//TODO i should use a repository layer to use redis.. ithink - check in the future

@Service
public class SessionService {

    final String key = "session:%s";
    private com.google.gson.Gson gson;

    @Autowired
    private RedisTemplate<String, Object> jedis;

    @Autowired
    private StringRedisTemplate jedisStr;

    //TODO research how to save session without session
    public Optional<Session> save(Session session) {

        String key = String.format(this.key, session.getSession());

        ValueOperations values = jedis.opsForValue();
        values.set(key, session, 3600, TimeUnit.SECONDS);

        return Optional.of(session);
    }

    public Optional<Session> findBySession(String session) {
        Optional<?> cacheSession = Optional.ofNullable(jedisStr.opsForValue().get(String.format(this.key, session)));

        if(cacheSession.isPresent()) {
            return (Optional<Session>) cacheSession;
        }

        return Optional.empty();
    }

}
