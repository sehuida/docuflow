package com.docuflow.app.core.entity;

import com.docuflow.app.core.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role;

    public static User create(String username, String encodedPassword, String role) {
        User user = new User();
        user.username = username;
        user.password = encodedPassword;
        user.role = role;
        return user;
    }
}