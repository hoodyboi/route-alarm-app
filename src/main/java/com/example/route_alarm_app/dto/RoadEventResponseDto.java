package com.example.route_alarm_app.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoadEventResponseDto {

    private Long roadEventId;

    private String eventType;

    private String location;
    private Integer severity;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
