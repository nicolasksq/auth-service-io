package com.budget.io.authserviceio.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class LoginResponse {
    int uid;
    String session;
}
