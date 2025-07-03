package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.RoadEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoadEventRepository extends JpaRepository<RoadEvent, Long> {
    // 이벤트 타입으로 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByEventType(String eventType);

    // 특정 심각도 이상의 도로 이벤트를 찾는 메서드
    List<RoadEvent> findBySeverityGreaterThanEqual(Integer severity);

    // 특정 위도/경도 범위 내에 있는 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByLatBetweenAndLngBetween(BigDecimal latStart, BigDecimal latEnd, BigDecimal lngStart, BigDecimal lngEnd);

    // 특정 시작 시간 이후에 발생한 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByStartedAtAfter(LocalDateTime startedAt);

    // 설명에 특정 키워드가 포함된 도로 이벤트를 찾는 메서드
    List<RoadEvent> findByDescriptionContaining(String keyword);
}
