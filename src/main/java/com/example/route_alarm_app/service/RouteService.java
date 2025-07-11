package com.example.route_alarm_app.service;

import com.example.route_alarm_app.domain.Route;
import com.example.route_alarm_app.domain.User;
import com.example.route_alarm_app.domain.RoadEvent;
import com.example.route_alarm_app.dto.RoadEventResponseDto;
import com.example.route_alarm_app.dto.RouteRequestDto;
import com.example.route_alarm_app.dto.RouteResponseDto;
import com.example.route_alarm_app.repository.RoadEventRepository;
import com.example.route_alarm_app.repository.RouteRepository;
import com.example.route_alarm_app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final RoadEventRepository roadEventRepository;
    private final GeometryFactory geometryFactory;
    private final ObjectMapper objectMapper;

    // 경로 생성 메서드
    @Transactional
    public RouteResponseDto createRoute(RouteRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        LineString path = convertWaypointToLineString(requestDto.getWaypoints());
        String routeUuid = (requestDto.getUuid() != null && !requestDto.getUuid().isEmpty()) ?
                requestDto.getUuid() : UUID.randomUUID().toString();

        Route route = Route.builder()
                .user(user)
                .routeName(requestDto.getRouteName())
                .path(path)
                .srcLat(requestDto.getSrcLat())
                .srcLng(requestDto.getSrcLng())
                .dstLat(requestDto.getDstLat())
                .dstLng(requestDto.getDstLng())
                .uuid(routeUuid)
                .build();

        Route savedRoute = routeRepository.save(route);

        return convertToDto(savedRoute);
    }

    // DTO의 waypoints 문자열을 LineString 객체로 변환하는 헬퍼 메소드
    private LineString convertWaypointToLineString(String waypointsJson){
        if(waypointsJson == null || waypointsJson.isEmpty()){
            return null;
        }
        try {
            List<Map<String, Double>> points = objectMapper.readValue(waypointsJson, new TypeReference<>() {});
            Coordinate[] coordinates = points.stream()
                    .map(point -> new Coordinate(point.get("lng"), point.get("lat")))
                    .toArray(Coordinate[]::new);
            return geometryFactory.createLineString(coordinates);
        } catch (IOException e){
            throw new IllegalArgumentException("잘못된 waypoints 형식입니다.", e);
        }
    }

    // LineString -> String(JSON) 변환 헬퍼
    private String convertLineStringToJson(LineString path){
        if(path == null) {
            return null;
        }

        try {
            Coordinate[] coordinates = path.getCoordinates();

            List<Map<String, Double>> points = Arrays.stream(coordinates)
                    .map(c -> Map.of("lat", c.y,"lng", c.x))
                    .collect(Collectors.toList());

            return objectMapper.writeValueAsString(points);
        } catch (JsonProcessingException e) {
            return "[]";
        }
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

        List<Route> routes = routeRepository.findByUserUserId(userId);

        return routes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 경로 업데이트 메서드
    @Transactional
    public RouteResponseDto updateRoute(Long routeId, RouteRequestDto requestDto) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다."));

        LineString newPath = convertWaypointToLineString(requestDto.getWaypoints());

        route.update(
                requestDto.getRouteName(),
                requestDto.getSrcLat(),
                requestDto.getSrcLng(),
                requestDto.getDstLat(),
                requestDto.getDstLng(),
                newPath
        );

        return convertToDto(route);
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

        List<RoadEvent> events = roadEventRepository.findEventsWithinDistance(centerLat, centerLng, distance);

        return events.stream()
                .map(RoadEventResponseDto::new)
                .collect(Collectors.toList());
    }

    // DTO 변환
    private RouteResponseDto convertToDto(Route route) {
        return new RouteResponseDto(
                route.getRouteId(),
                route.getUser().getUserId(),
                route.getRouteName(),
                route.getSrcLat(),
                route.getSrcLng(),
                route.getDstLat(),
                route.getDstLng(),
                convertLineStringToJson(route.getPath()),
                route.getCreatedAt(),
                route.getUpdatedAt(),
                route.getIsDeleted(),
                route.getUuid()
        );
    }
}
