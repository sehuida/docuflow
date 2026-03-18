package com.docuflow.app.core.dto.request;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private String username;
    private String password;
    private String role;
}