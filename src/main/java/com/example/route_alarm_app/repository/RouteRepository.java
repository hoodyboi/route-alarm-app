package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
    List<Route> findByUserID(Long userId);

    Optional<Route> findByUuid(String uuid);

    Optional<Route> findByUserIdAndRouteName(Long userId, String routeName);
}
