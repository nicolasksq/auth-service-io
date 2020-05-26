package com.budget.io.authserviceio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class SessionJedis implements Serializable {

    public SessionJedis(long uid, String refreshToken, String session) {


        this.uid = uid;
        this.refreshToken = refreshToken;

        if(session == null) {
            Random random = new Random();
            int val = random.nextInt();
            this.session = Integer.toHexString(val);
        }else{
            this.session = session;
        }
    }

    @JsonProperty("uid")
    @Id
    private long uid;

    @Setter(AccessLevel.NONE)
    @JsonProperty("session")
    private String session;

    @JsonProperty("refresh_token")
    private String refreshToken;

}
