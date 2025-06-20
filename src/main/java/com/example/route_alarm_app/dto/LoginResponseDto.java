package com.example.route_alarm_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String loginId;
    private String accessToken;
}
