package com.example.route_alarm_app.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.LineString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDto {
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "경로 이름은 필수입니다.")
    private String routeName;

    @NotNull(message = "출발지 위도는 필수입니다.")
    @DecimalMin(value = "-90.0", message = "위도는 -90.0 이상이어야 합니다.")
    @DecimalMax(value = "90.0", message = "위도는 90.0 이하이어야 합니다.")
    private BigDecimal srcLat;
    private BigDecimal srcLng;

    @NotNull(message = "도착지 위도는 필수입니다.")
    private BigDecimal dstLat;

    @NotNull(message = "도착지 경도는 필수입니다.")
    private BigDecimal dstLng;
    private String waypoints;
    private String uuid;
}
