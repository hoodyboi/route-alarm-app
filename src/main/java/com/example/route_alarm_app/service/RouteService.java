package com.example.route_alarm_app.service;

import com.example.route_alarm_app.domain.Route;
import com.example.route_alarm_app.domain.User;
import com.example.route_alarm_app.domain.RoadEvent;
import com.example.route_alarm_app.dto.RoadEventResponseDto;
import com.example.route_alarm_app.dto.RouteRequestDto;
import com.example.route_alarm_app.dto.RouteResponseDto;
import com.example.route_alarm_app.dto.RoadEventRequestDto;
import com.example.route_alarm_app.repository.RoadEventRepository;
import com.example.route_alarm_app.repository.RouteRepository;
import com.example.route_alarm_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final RoadEventRepository roadEventRepository;


    // 경로 생성 메서드
    @Transactional
    public RouteResponseDto createRoute(RouteRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        String routeUuid = (requestDto.getUuid() != null && !requestDto.getUuid().isEmpty()) ?
                requestDto.getUuid() : UUID.randomUUID().toString();

        Route route = Route.builder()
                .userId(requestDto.getUserId())
                .routeName(requestDto.getRouteName())
                .srcLat(requestDto.getSrcLat())
                .srcLng(requestDto.getSrcLng())
                .dstLat(requestDto.getDstLat())
                .dstLng(requestDto.getDstLng())
                .waypoints(requestDto.getWaypoints())
                .uuid(routeUuid)
                .build();

        Route savedRoute = routeRepository.save(route);

        return convertToDto(savedRoute);
    }

    // 특정 경로 조회 메서드
    @Transactional(readOnly = true)
    public RouteResponseDto getRouteInfo(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다. (ID: " + routeId + ")"));
        return convertToDto(route);
    }

    // 특정 사용자의 모든 경로 목록 조회 메서드
    @Transactional(readOnly = true)
    public List<RouteResponseDto> getAllRoutesByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<Route> routes = routeRepository.findByUserId(userId);

        return routes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 경로 업데이트 메서드
    @Transactional
    public RouteResponseDto updateRoute(Long routeId, RouteRequestDto requestDto) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다. (ID: " + routeId + ")"));

        String finalRouteName = requestDto.getRouteName() != null ? requestDto.getRouteName() : route.getRouteName();
        BigDecimal finalSrcLat = requestDto.getSrcLat() != null ? requestDto.getSrcLat() : route.getSrcLat();
        BigDecimal finalSrcLng = requestDto.getSrcLng() != null ? requestDto.getSrcLng() : route.getSrcLng();
        BigDecimal finalDstLat = requestDto.getDstLat() != null ? requestDto.getDstLat() : route.getDstLat();
        BigDecimal finalDstLng = requestDto.getDstLng() != null ? requestDto.getDstLng() : route.getDstLng();
        String finalWaypoints = requestDto.getWaypoints() != null ? requestDto.getWaypoints() : route.getWaypoints();

        route.update(finalRouteName, finalSrcLat, finalSrcLng, finalDstLat, finalDstLng, finalWaypoints);

        Route updateRoute = routeRepository.save(route);

        return convertToDto(updateRoute);
    }

    // 경로 삭제 메서드
    @Transactional
    public void deleteRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다. (ID: " + routeId + ")"));

        route.delete();
        routeRepository.save(route);
    }

    // 특정 경로 주변의 돌발 이벤트를 검색하는 메소드
    @Transactional(readOnly = true)
    public List<RoadEventResponseDto> findNearbyEventForRoute(Long routeId, int distance){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(()-> new IllegalArgumentException("경로를 찾을 수 없습니다."));

        double centerLat = (route.getSrcLat().doubleValue() + route.getDstLat().doubleValue()) / 2;
        double centerLng = (route.getSrcLat().doubleValue() + route.getDstLng().doubleValue()) / 2;

        List<RoadEvent> events = roadEventRepository.findEventsWithDistance(centerLat, centerLng, distance);

        return events.stream()
                .map(RoadEventResponseDto::new)
                .collect(Collectors.toList());
    }

    // DTO 변환
    private RouteResponseDto convertToDto(Route route) {
        return new RouteResponseDto(
                route.getRouteId(),
                route.getUserId(),
                route.getRouteName(),
                route.getSrcLat(),
                route.getSrcLng(),
                route.getDstLat(),
                route.getDstLng(),
                route.getWaypoints(),
                route.getCreatedAt(),
                route.getUpdatedAt(),
                route.getIsDeleted(),
                route.getUuid()
        );
    }
}
