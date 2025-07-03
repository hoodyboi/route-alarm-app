package com.example.route_alarm_app.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoadEventRequestDto {

    @NotBlank(message = "이벤트 유형은 필수입니다.")
    @Size(max = 20, message = "이벤트 유형은 20자를 초과할 수 없습니다.")
    private String eventType;

    @NotBlank(message = "위치 정보는 필수입니다")
    @Size(max = 100, message = "위치 정보는 100자 이하여야 합니다.")
    private String location;
    private Integer severity;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    @Size(max = 255, message = "설명은 255자를 초과할 수 없습니다.")
    private String description;
}
