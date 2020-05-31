package com.budget.io.authserviceio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Random;

@Getter
@Setter
public class Session implements Serializable {

    public Session() {}

    public Session(long uid, String refreshToken,String accessToken, String session) {
        this.uid = uid;
        this.accessToken = accessToken;
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

    @JsonProperty("access_token")
    private String accessToken;
}
