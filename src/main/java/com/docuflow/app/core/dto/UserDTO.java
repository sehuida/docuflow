package com.docuflow.app.core.dto;

import com.docuflow.app.core.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
    private Role role;
}

