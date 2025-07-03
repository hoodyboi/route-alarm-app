package com.example.route_alarm_app.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "road_event_id", nullable = false)
    private Long roadEventId;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "message", nullable = false, length = 256)
    private String message;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Notification(Long userId, Long routeId, Long roadEventId, LocalDateTime sentAt, LocalDateTime readAt, String message){
        this.userId = userId;
        this.routeId = routeId;
        this.roadEventId = roadEventId;
        this.sentAt = sentAt;
        this.readAt = readAt;
        this.message = message;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(LocalDateTime readAt, String message){
        this.readAt = readAt;
        this.message = message;

        this.updatedAt = LocalDateTime.now();
    }
}
