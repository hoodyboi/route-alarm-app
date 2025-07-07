package com.example.route_alarm_app.dto;

import com.example.route_alarm_app.domain.RoadEvent;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class RoadEventResponseDto {

    private Long roadEventId;
    private String eventType;
    private Integer severity;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private double lat;
    private double lng;

    public RoadEventResponseDto(RoadEvent entity){
        this.roadEventId = entity.getRoadEventId();
        this.eventType = entity.getEventType();
        this.description = entity.getDescription();
        this.severity = entity.getSeverity();
        this.startedAt = entity.getStartedAt();
        this.endedAt = entity.getEndedAt();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();

        if(entity.getLocation() != null){
            this.lat = entity.getLocation().getY();
            this.lng = entity.getLocation().getX();
        }
    }

}
