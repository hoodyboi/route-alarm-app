package com.example.route_alarm_app.controller;

import com.example.route_alarm_app.dto.UserResponseDto;
import com.example.route_alarm_app.dto.UserSignUpRequestDto;
import com.example.route_alarm_app.dto.UserUpdateRequestDto;
import com.example.route_alarm_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserSignUpRequestDto requestDto){
        UserResponseDto responseDto = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 사용자 정보 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long userId){
        UserResponseDto responseDto = userService.getUserInfo(userId);

        return ResponseEntity.ok(responseDto);
    }

    // 사용자 정보 업데이트 API
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequestDto requestDto){
        UserResponseDto responseDto = userService.updatedUser(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 탈퇴 API
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
