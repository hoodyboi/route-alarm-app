package com.example.route_alarm_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {

    @NotBlank(message = "로그인 아이디는 필수 입력 값입니다.")
    @Size(min = 4, max = 16, message = "로그인 아이디는 4~16자여야 합니다")
    private String loginId;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Size(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
    private String password;

    private String role;
    private String oauthType;
    private String oauthId;
    private String uuid;
}
