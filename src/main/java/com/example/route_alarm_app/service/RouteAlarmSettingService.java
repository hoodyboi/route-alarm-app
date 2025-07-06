package com.example.route_alarm_app.service;

import com.example.route_alarm_app.domain.RouteAlarmSetting;
import com.example.route_alarm_app.dto.RouteAlarmSettingResponseDto;
import com.example.route_alarm_app.dto.RouteAlarmSettingUpdateRequestDto;
import com.example.route_alarm_app.repository.RouteAlarmSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RouteAlarmSettingService {

    private final RouteAlarmSettingRepository settingRepository;

    @Transactional(readOnly = true)
    public RouteAlarmSettingResponseDto getSetting(Long routeId){
        RouteAlarmSetting setting = settingRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("설정 정보 없음"));
        return new RouteAlarmSettingResponseDto(setting);
    }

    @Transactional
    public void updateSetting(Long routeId, RouteAlarmSettingUpdateRequestDto requestDto){
        RouteAlarmSetting setting = settingRepository.findById(routeId)
                .orElseThrow(()-> new IllegalArgumentException("설정 정보 없음"));

        setting.update(requestDto.getProtestAlert(), requestDto.getConstructionAlert(), requestDto.getCongestionAlert());
    }
}
