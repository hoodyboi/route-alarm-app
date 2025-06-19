package com.example.route_alarm_app.service;

import com.example.route_alarm_app.domain.User;
import com.example.route_alarm_app.dto.UserUpdateRequestDto;
import com.example.route_alarm_app.repository.UserRepository;
import com.example.route_alarm_app.dto.UserSignUpRequestDto;
import com.example.route_alarm_app.dto.UserLoginRequestDto;
import com.example.route_alarm_app.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto signup(UserSignUpRequestDto requestDto){
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .loginId(requestDto.getLoginId())
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .role(requestDto.getRole() != null ? requestDto.getRole() : "ROLE_USER")
                .oauthId(requestDto.getOauthId())
                .oauthType(requestDto.getOauthType())
                .uuid(requestDto.getUuid())
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getUserId(),
                savedUser.getLoginId(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt(),
                savedUser.getOauthType(),
                savedUser.getUuid()
        );
    }

    public UserResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return new UserResponseDto(
                user.getUserId(),
                user.getLoginId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getOauthType(),
                user.getUuid()
        );
    }

    public UserResponseDto updatedUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (requestDto.getEmail() != null && !requestDto.getEmail().isEmpty()) {
            user.update(requestDto.getEmail(), user.getPassword(), user.getRole());
        }
        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()) {
            String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
            user.update(user.getEmail(), encodedNewPassword, user.getRole());
        }
        if (requestDto.getRole() != null && !requestDto.getRole().isEmpty()) {
            user.update(user.getEmail(), user.getPassword(), requestDto.getRole());
        }

        user.update(
                requestDto.getEmail() != null ? requestDto.getEmail() : user.getEmail(),
                requestDto.getNewPassword() != null ? passwordEncoder.encode(requestDto.getNewPassword()) : user.getPassword(),
                requestDto.getRole() != null ? requestDto.getRole() : user.getRole()
        );

        User updateUser = userRepository.save(user);

        return new UserResponseDto(
                updateUser.getUserId(),
                updateUser.getLoginId(),
                updateUser.getEmail(),
                updateUser.getRole(),
                updateUser.getCreatedAt(),
                updateUser.getUpdatedAt(),
                updateUser.getOauthType(),
                updateUser.getUuid()
        );
    }
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        user.delete();
        userRepository.save(user);
    }
}

