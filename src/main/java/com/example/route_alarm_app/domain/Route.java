package com.example.route_alarm_app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.LineString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    @Column(columnDefinition = "geography(LineString, 4326)")
    private LineString path;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "uuid", length = 36, unique = true)
    private String uuid;

    @Builder
    public Route(User user, String routeName, LineString path, BigDecimal srcLat, BigDecimal srcLng, BigDecimal dstLat,
                 BigDecimal dstLng, String uuid){
        this.user = user;
        this.routeName = routeName;
        this.path = path;
        this.srcLat = srcLat;
        this.srcLng = srcLng;
        this.dstLat = dstLat;
        this.dstLng = dstLng;
        this.uuid = uuid;

        // 필드 기본값 설정
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void update(String routeName, BigDecimal srcLat, BigDecimal srcLng, BigDecimal dstLat, BigDecimal dstLng,
                       LineString path){
        if(routeName != null) { this.routeName = routeName; }
        if(path != null) { this.path = path; }
        if(srcLat != null) { this.srcLat = srcLat; }
        if(srcLng != null) { this.srcLng = srcLng; }
        if(dstLat != null) { this.dstLat = dstLat; }
        if(dstLng != null) { this.dstLng = dstLng; }
    }

    public void delete() {
        this.isDeleted = true;
        this.updatedAt = LocalDateTime.now();
    }
}
