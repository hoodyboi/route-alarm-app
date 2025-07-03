package com.example.route_alarm_app.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "road_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RoadEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "road_event_id")
    private Long roadEventId;

    @Column(name = "event_type", nullable = false, length = 20)
    private String eventType;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "severity")
    private Integer severity;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public RoadEvent(String eventType, String location, Integer severity, LocalDateTime startedAt, LocalDateTime endedAt,
                     String description){
        this.eventType = eventType;
        this.location = location;
        this.severity = severity;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.description = description;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String eventType, String location, Integer severity, LocalDateTime startedAt, LocalDateTime endedAt,
                       String description){
        this.eventType = eventType;
        this.location = location;
        this.severity = severity;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.description = description;

        this.updatedAt = LocalDateTime.now();
    }
}
