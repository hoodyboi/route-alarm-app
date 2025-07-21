package com.example.route_alarm_app.dto;

import com.example.route_alarm_app.domain.RouteAlarmSetting;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RouteAlarmSettingResponseDto {

    private Long routeId;
    private Boolean protestAlert;
    private Boolean constructionAlert;
    private Boolean congestionAlert;

    public RouteAlarmSettingResponseDto(RouteAlarmSetting entity){
        this.routeId = entity.getRoute().getRouteId();

        this.protestAlert = entity.getProtestAlert();
        this.constructionAlert = entity.getConstructionAlert();
        this.congestionAlert = entity.getCongestionAlert();
    }
}
