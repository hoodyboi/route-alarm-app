package com.example.route_alarm_app.domain;

import com.example.route_alarm_app.dto.AccInfoRow;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.locationtech.jts.geom.Point;

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

    // 공공 API에서 주는 돌발 아이디
    @Column(name = "acc_id", unique = true)
    private Long accId;

    @Column(name = "event_type", nullable = false, length = 20)
    private String eventType;

    @Column(columnDefinition = "geography(Point,4326)")
    private Point location;

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
    public RoadEvent(Long accId, String eventType, Point location, Integer severity, LocalDateTime startedAt, LocalDateTime endedAt,
                     String description){
        this.accId = accId;
        this.eventType = eventType;
        this.location = location;
        this.severity = severity;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.description = description;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(AccInfoRow row, Point newLocationPoint){
        this.eventType = row.getAccType();
        this.description = row.getAccInfo();
        this.location = newLocationPoint;

        this.updatedAt = LocalDateTime.now();
    }
}
