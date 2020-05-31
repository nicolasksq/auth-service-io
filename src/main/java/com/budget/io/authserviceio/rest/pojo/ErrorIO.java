package com.budget.io.authserviceio.rest.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ErrorIO extends Exception{
    private int error;
    private String message;
}
