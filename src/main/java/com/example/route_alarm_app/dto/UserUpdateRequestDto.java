package com.example.route_alarm_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    @Size(min = 8, max = 255, message = "비밀번호는 8~20자여야 합니다.")
    private String newPassword;

    private String role;
}
