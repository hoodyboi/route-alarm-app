package com.example.route_alarm_app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RouteAlarmSettingUpdateRequestDto {

    private Boolean protestAlert;
    private Boolean congestionAlert;
    private Boolean constructionAlert;
}
