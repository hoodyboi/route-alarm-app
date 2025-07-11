package com.example.route_alarm_app.domain;

import com.example.route_alarm_app.dto.RouteAlarmSettingUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_alarm_settings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RouteAlarmSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", unique = true)
    private Route route;

    @Column(name = "protest_alert", nullable = false)
    private Boolean protestAlert = true;

    @Column(name = "construction_alert", nullable = false)
    private Boolean constructionAlert = true;

    @Column(name = "congestion_alert", nullable = false)
    private Boolean congestionAlert = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public RouteAlarmSetting(Route route){
        this.route = route;
    }

    public void update(RouteAlarmSettingUpdateRequestDto requestDto){
        this.protestAlert = requestDto.getProtestAlert();
        this.constructionAlert = requestDto.getConstructionAlert();
        this.congestionAlert = requestDto.getCongestionAlert();
    }

}
