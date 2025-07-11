package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.Route;
import com.example.route_alarm_app.domain.RouteAlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RouteAlarmSettingRepository extends JpaRepository<RouteAlarmSetting, Long> {

    Optional<RouteAlarmSetting> findByRoute(Route route);
}
