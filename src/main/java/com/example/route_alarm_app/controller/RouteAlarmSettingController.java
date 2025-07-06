package com.example.route_alarm_app.controller;

import com.example.route_alarm_app.dto.RouteAlarmSettingResponseDto;
import com.example.route_alarm_app.dto.RouteAlarmSettingUpdateRequestDto;
import com.example.route_alarm_app.service.RouteAlarmSettingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes/{routesId}/alarm-settings")
public class RouteAlarmSettingController {

    private final RouteAlarmSettingService settingService;

    @GetMapping
    public ResponseEntity<RouteAlarmSettingResponseDto> getSetting(@PathVariable Long routeId){
        RouteAlarmSettingResponseDto responseDto = settingService.getSetting(routeId);

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateSetting(
            @PathVariable Long routeId,
            @RequestBody RouteAlarmSettingUpdateRequestDto requestDto){
        settingService.updateSetting(routeId, requestDto);
        return ResponseEntity.ok().build();
    }
}
