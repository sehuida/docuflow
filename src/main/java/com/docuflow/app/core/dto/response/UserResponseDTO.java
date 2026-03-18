package com.docuflow.app.core.dto.response;

import com.docuflow.app.core.enums.DocumentStatus;
import com.docuflow.app.core.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private Role role;
}