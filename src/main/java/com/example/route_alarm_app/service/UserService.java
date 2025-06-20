package com.example.route_alarm_app.service;

import com.example.route_alarm_app.domain.User;
import com.example.route_alarm_app.dto.*;
import com.example.route_alarm_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional
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

    @Transactional
    public UserResponseDto updatedUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        String finalEmail = user.getEmail();
        String finalPassword = user.getPassword();
        String finalRole = user.getRole();

        if (requestDto.getEmail() != null && !requestDto.getEmail().isEmpty()) {
            finalEmail = requestDto.getEmail();
        }
        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()) {
            finalPassword = passwordEncoder.encode(requestDto.getNewPassword());
        }
        if (requestDto.getRole() != null && !requestDto.getRole().isEmpty()) {
            finalRole = requestDto.getRole();
        }

        user.update(finalEmail, finalPassword, finalRole);

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

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        user.delete();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(UserLoginRequestDto requestDto){
        User user = userRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));// 사용자 없을 경우 예외

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        String dummyToken = "dummy_jwt_token_for_" + user.getLoginId();

        return new LoginResponseDto(
                user.getUserId(),
                user.getLoginId(),
                dummyToken //임시토큰 반환
        );

    }

}

