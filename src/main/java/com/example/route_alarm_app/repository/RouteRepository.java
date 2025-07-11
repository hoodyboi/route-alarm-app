package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
    List<Route> findByUserUserId(Long userId);

    Optional<Route> findByUuid(String uuid);

    Optional<Route> findByUserUserIdAndRouteName(Long userId, String routeName);

    @Query(value = "SELECT * FROM routes WHERE ST_DWithin(path, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography, :distance)", nativeQuery = true)
    List<Route> findRoutesWithinDistance(@Param("lat") double lat, @Param("lng") double lng, @Param("distance") int distance);
}
