package com.example.route_alarm_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_alarm_settings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RouteAlarmSetting {

    @Id
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "protest_alert", nullable = false)
    private Boolean protestAlert;

    @Column(name = "construction_alert", nullable = false)
    private Boolean constructionAlert;

    @Column(name = "congestion_alert", nullable = false)
    private Boolean congestionAlert;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public RouteAlarmSetting(Long routeId, Boolean protestAlert, Boolean constructionAlert, Boolean congestionAlert){
        this.routeId = routeId;
        this.protestAlert = protestAlert;
        this.constructionAlert = constructionAlert;
        this.congestionAlert = congestionAlert;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(Boolean protestAlert, Boolean constructionAlert, Boolean congestionAlert){
        this.protestAlert = protestAlert;
        this.constructionAlert = constructionAlert;
        this.congestionAlert = congestionAlert;
        this.updatedAt = LocalDateTime.now();
    }

}
