package com.docuflow.app.core.controller;

import com.docuflow.app.core.dto.UserDTO;
import com.docuflow.app.core.dto.request.UserRegisterRequest;
import com.docuflow.app.core.dto.response.UserResponseDTO;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody UserRegisterRequest request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }
}
