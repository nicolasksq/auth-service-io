package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.SessionJedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

//TODO i should use a repository layer to use redis.. ithink - check in the future

@Service
public class SessionService {


    @Autowired
    private RedisTemplate< String, Object > template;

    public SessionJedis save(SessionJedis sessionJedis){

        final String key = String.format( "session:%s", sessionJedis.getSession() );

        properties.put( "uid", sessionJedis.getUid() );
        properties.put( "refreshToken", sessionJedis.getRefreshToken() );

        template.expire( key, 3600, TimeUnit.SECONDS );
        template.opsForSet().putAll( key, properties);

        return sessionJedis;
    }

    public Optional<SessionJedis> findBySession(String session){

        if(session != null) {

            final String key = String.format("session:%s", session);

            Boolean exist = template.hasKey(session);

            if (exist) {
                final long uid = (long) template.opsForHash().get(key, "uid");
                final String refreshToken = (String) template.opsForHash().get(key, "refreshToken");

                return Optional.of(new SessionJedis(uid, refreshToken, session));
            }
        }

        return Optional.empty();
    }





}
