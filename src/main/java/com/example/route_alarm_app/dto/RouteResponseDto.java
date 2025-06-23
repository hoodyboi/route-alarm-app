package com.example.route_alarm_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDto {
    private Long routeId;
    private Long userId;
    private String routeName;
    private BigDecimal srcLat;
    private BigDecimal srcLng;
    private BigDecimal dstLat;
    private BigDecimal dstLng;
    private String waypoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String uuid;
}
