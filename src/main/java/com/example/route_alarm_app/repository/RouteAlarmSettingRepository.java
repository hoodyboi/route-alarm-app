package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.RouteAlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteAlarmSettingRepository extends JpaRepository<RouteAlarmSetting, Long> {
}
