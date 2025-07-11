package com.example.route_alarm_app.service;


import com.example.route_alarm_app.domain.Route;
import com.example.route_alarm_app.domain.RouteAlarmSetting;
import com.example.route_alarm_app.dto.RouteAlarmSettingResponseDto;
import com.example.route_alarm_app.dto.RouteAlarmSettingUpdateRequestDto;
import com.example.route_alarm_app.repository.RouteAlarmSettingRepository;
import com.example.route_alarm_app.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteAlarmSettingService {

    private final RouteRepository routeRepository;
    private final RouteAlarmSettingRepository routeAlarmSettingRepository;

    // 특정 경로의 알림 설정을 조회하는 메소드
    public RouteAlarmSettingResponseDto getAlarmSetting(Long routeId){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다."));

        RouteAlarmSetting setting = routeAlarmSettingRepository.findByRoute(route)
                .orElseGet(()-> {
                    RouteAlarmSetting newSetting = RouteAlarmSetting.builder().route(route).build();
                    return routeAlarmSettingRepository.save(newSetting);
                });
        return new RouteAlarmSettingResponseDto(setting);
    }

    //특정 경로의 알림 설정을 업데이트하는 메소드
    @Transactional
    public RouteAlarmSettingResponseDto updateAlarmSetting(Long routeId, RouteAlarmSettingUpdateRequestDto requestDto){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다."));

        RouteAlarmSetting setting = routeAlarmSettingRepository.findByRoute(route)
                .orElseGet(() -> {
                    RouteAlarmSetting newSetting = RouteAlarmSetting.builder().route(route).build();
                    return routeAlarmSettingRepository.save(newSetting);
                });

        setting.update(requestDto);

        return new RouteAlarmSettingResponseDto(setting);
    }
}
