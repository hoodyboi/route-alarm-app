package com.example.route_alarm_app.dto;

import com.example.route_alarm_app.domain.RouteAlarmSetting;
import lombok.Getter;

@Getter
public class RouteAlarmSettingResponseDto {

    private Long routeId;
    private Boolean protestAlert;
    private Boolean constructionAlert;
    private Boolean congestionAlert;

    public RouteAlarmSettingResponseDto(RouteAlarmSetting entity){
        this.routeId = entity.getRouteId();

        this.protestAlert = entity.getProtestAlert();
        this.constructionAlert = entity.getConstructionAlert();
        this.congestionAlert = entity.getCongestionAlert();
    }
}
