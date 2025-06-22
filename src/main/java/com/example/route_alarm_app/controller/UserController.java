package com.example.route_alarm_app.controller;

import com.example.route_alarm_app.dto.*;
import com.example.route_alarm_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

// Swagger (Springdoc OpenAPI) 어노테이션 임포트
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @Operation(summary = "사용자 회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 유효성 검증 실패")
    @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디 혹은 이메일")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(
            @org.springframework.web.bind.annotation.RequestBody // Spring의 @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "회원가입 요청 정보", required = true) @Valid UserSignUpRequestDto requestDto){
        UserResponseDto responseDto = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 사용자 정보 조회 API
    @Operation(summary = "사용자 정보 조회", description = "특정 사용자의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@Parameter(description = "조회할 사용자 ID", example = "1", required = true)
                                                           @PathVariable Long userId){
        UserResponseDto responseDto = userService.getUserInfo(userId);

        return ResponseEntity.ok(responseDto);
    }

    // 로그인 API (POST /api/users/login)
    @Operation(summary = "사용자 로그인", description = "사용자 아이디와 비밀번호로 로그인하고, 인증 토큰을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = LoginResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "아이디 또는 비밀번호 불일치")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @org.springframework.web.bind.annotation.RequestBody // Spring의 @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "로그인 요청 정보", required = true) // Swagger의 @RequestBody
            @Valid UserLoginRequestDto requestDto) {
        LoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 정보 업데이트 API
    @Operation(summary = "사용자 정보 업데이트", description = "특정 사용자의 이메일, 비밀번호, 역할 등을 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "업데이트 성공",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 유효성 검증 실패")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = "업데이트할 사용자 ID", example = "1", required = true)
            @PathVariable Long userId,
            @org.springframework.web.bind.annotation.RequestBody // Spring의 @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "업데이트할 사용자 정보", required = true) // Swagger의 @RequestBody
            @Valid UserUpdateRequestDto requestDto){
        UserResponseDto responseDto = userService.updatedUser(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 탈퇴 API
    @Operation(summary = "사용자 탈퇴", description = "특정 사용자를 논리적으로 삭제 처리합니다. (is_deleted = true)")
    @ApiResponse(responseCode = "204", description = "탈퇴 성공(응답 본문 없음)")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "탈퇴할 사용자 ID", example = "1", required = true)
            @PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
