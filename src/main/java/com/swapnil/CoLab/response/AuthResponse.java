package com.swapnil.CoLab.response;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean status;
    private String message;

}
