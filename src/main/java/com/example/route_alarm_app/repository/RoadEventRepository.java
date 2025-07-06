package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.RoadEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoadEventRepository extends JpaRepository<RoadEvent, Long> {

    // 이벤트 타입으로 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByEventType(String eventType);

    // 특정 심각도 이상의 도로 이벤트를 찾는 메서드
    List<RoadEvent> findBySeverityGreaterThanEqual(Integer severity);

    // 특정 시작 시간 이후에 발생한 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByStartedAtAfter(LocalDateTime startedAt);

    // 설명에 특정 키워드가 포함된 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByDescriptionContaining(String keyword);

    /**
     * 특정 지점으로부터 일정 거리(미터 단위) 내의 모든 도로 이벤트를 찾는 메소드
     * @param lat 위도
     * @param lng 경도
     * @param distanceInMeters 검색할 반경 (미터)
     * @return List<RoadEvent>
     */
    @Query(value = "SELECT * FROM road_events WHERE ST_DWithin(location, ST_MakePoint(:lng, :lat), :distance)", nativeQuery = true)
    List<RoadEvent> findEventsWithDistance(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("distance") int distanceInMeters
    );

}
