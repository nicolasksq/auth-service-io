package com.budget.io.authserviceio.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
@AllArgsConstructor
public class RefreshTokenRequest {

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("grant_type")
    private final String grantType = "refresh_token";
}
