package com.example.route_alarm_app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "routed")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "route_name", nullable = false, length = 50)
    private String routeName;

    @Column(name = "src_lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal srcLat;

    @Column(name = "src_lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal srcLng;

    @Column(name = "dst_lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal dstLat;

    @Column(name = "dst_lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal dstLng;

    @Column(name = "waypoints", columnDefinition = "jsonb")
    private String waypoints;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "uuid", length = 36, unique = true)
    private String uuid;

    @Builder
    public Route(Long userId, String routeName, BigDecimal srcLat, BigDecimal srcLng, BigDecimal dstLat, BigDecimal dstLng,
                 String waypoints, String uuid){
        this.userId = userId;
        this.routeName = routeName;
        this.srcLat = srcLat;
        this.srcLng = srcLng;
        this.dstLat = dstLat;
        this.dstLng = dstLng;
        this.uuid = uuid;
        this.waypoints = waypoints;

        // 필드 기본값 설정
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void update(String routeName, BigDecimal srcLat, BigDecimal srcLng, BigDecimal dstLat, BigDecimal dstLng,
                       String waypoints){
        this.userId = routeId;
        this.srcLat = srcLat;
        this.srcLng = srcLng;
        this.dstLat = dstLat;
        this.dstLng = dstLng;
        this.waypoints = waypoints;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.isDeleted = true;
        this.updatedAt = LocalDateTime.now();
    }
}
