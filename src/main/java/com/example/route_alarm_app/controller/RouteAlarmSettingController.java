package com.example.route_alarm_app.controller;

import com.example.route_alarm_app.dto.RouteAlarmSettingResponseDto;
import com.example.route_alarm_app.dto.RouteAlarmSettingUpdateRequestDto;
import com.example.route_alarm_app.service.RouteAlarmSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes/{routeId}/alarm-settings")
public class RouteAlarmSettingController {

    private final RouteAlarmSettingService routeAlarmSettingService;

    // 특정 경로의 알림 설정 조회 API
    @GetMapping
    public ResponseEntity<RouteAlarmSettingResponseDto> getAlarmSetting(@PathVariable Long routeId){
        RouteAlarmSettingResponseDto settingDto = routeAlarmSettingService.getAlarmSetting(routeId);
        return ResponseEntity.ok(settingDto);
    }

    // 특정 경로의 알림 설정 업데이트 API
    @PutMapping
    public ResponseEntity<RouteAlarmSettingResponseDto> updateAlarmSetting(
            @PathVariable Long routeId,
            @RequestBody @Valid RouteAlarmSettingUpdateRequestDto requestDto
    ){
        RouteAlarmSettingResponseDto updatedSettingDto = routeAlarmSettingService.updateAlarmSetting(routeId, requestDto);
        return ResponseEntity.ok(updatedSettingDto);
    }
}
