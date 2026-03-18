package com.docuflow.app.core.service;

import com.docuflow.app.core.dto.UserDTO;
import com.docuflow.app.core.dto.request.UserRegisterRequest;
import com.docuflow.app.core.dto.response.UserResponseDTO;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO register(UserRegisterRequest request) {

        // 1. 유효성 체크
        validate(request);

        // 2. 중복 체크
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자");
        }

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 4. Entity 생성
        User user = User.create(
                request.getUsername(),
                encodedPassword,
                request.getRole()
        );

        // 5. 저장
        User saved = userRepository.save(user);

        // 6. DTO 반환
        return UserResponseDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .role(saved.getRole())
                .build();
    }

    private void validate(UserRegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("username 필수");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("비밀번호 6자 이상");
        }
    }
}

