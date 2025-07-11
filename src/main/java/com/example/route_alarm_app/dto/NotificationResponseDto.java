package com.example.route_alarm_app.dto;

import com.example.route_alarm_app.domain.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private Long notificationId;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    private Long routeId;
    private String routeName;
    private Long roadEventId;
    private String eventDescription;

    public NotificationResponseDto(Notification entity){
        this.notificationId = entity.getId();
        this.message = entity.getMessage();
        this.isRead = entity.isRead();
        this.createdAt = entity.getCreatedAt();

        if(entity.getRoute() != null){
            this.routeId = entity.getRoute().getRouteId();
            this.routeName = entity.getRoute().getRouteName();
        }

        if(entity.getRoadEvent() != null){
            this.roadEventId = entity.getRoadEvent().getRoadEventId();
            this.eventDescription = entity.getRoadEvent().getDescription();
        }
    }
}
