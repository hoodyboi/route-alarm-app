package com.example.route_alarm_app.controller;

import com.example.route_alarm_app.dto.RouteRequestDto;
import com.example.route_alarm_app.dto.RouteResponseDto;
import com.example.route_alarm_app.service.RouteService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;


@Tag(name = "경로", description = "사용자 경로 관련 API")
@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    // 경로 생성 API (POST /api/routes)
    @Operation(summary = "경로 생성", description = "새로운 사용자 경로를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "경로 생성 성공",
            content = @Content(schema = @Schema(implementation = RouteResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 유효성 검증 실패 (예: 필수 필드 누락)")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @PostMapping
    public ResponseEntity<RouteResponseDto> createRoute(
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(description = "경로 생성 요청 정보", required = true) @Valid RouteRequestDto requestDto
    ){
        RouteResponseDto responseDto = routeService.createRoute(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 특정 경로 조회 API (GET /api/routes/{routeId})
    public ResponseEntity<RouteResponseDto> getRouteInfo(
            @Parameter(description = "조회할 경로 ID", example = "1", required = true)
            @PathVariable Long routeId){
        RouteResponseDto responseDto = routeService.getRouteInfo(routeId);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 사용자의 모든 경로 조회 API (GET /api/routes/user/{userID}
    @Operation(summary = "사용자별 경로 목록 조회", description = "특정 사용자가 등록한 모든 경로 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = RouteResponseDto.class, type = "array")))
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RouteResponseDto>> getAllRoutesByUserId(
            @Parameter(description = "경로를 조회할 사용자 ID", example = "1", required = true)
            @PathVariable Long userId){
        List<RouteResponseDto> responseDtos = routeService.getAllRoutesByUserId(userId);
        return ResponseEntity.ok(responseDtos);
    }

    // 경로 업데이트 API (PUT /api/routes/{routeId})
    @Operation(summary = "경로 정보 업데이트", description = "특정 경로 ID를 통해 경로 정보를 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "덥데이트 성공",
            content = @Content(schema = @Schema(implementation = RouteResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 유효성 검증 실패")
    @ApiResponse(responseCode = "404", description = "경로를 찾을 수 없음")
    @PutMapping("/{routeId}")
    public ResponseEntity<RouteResponseDto> updateRoute(
            @Parameter(description = "업데이트할 경로 ID", example = "1", required = true)
            @PathVariable Long routeId,
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(description = "경로 업데이트 요청 정보", required = true)
            @Valid RouteRequestDto requestDto){
        RouteResponseDto responseDto = routeService.updateRoute(routeId, requestDto);
        return ResponseEntity.ok(responseDto);
    }


    // 경로 삭제 API (DELETE /api/routes/{routeId})
    @Operation(summary = "경로 삭제", description = "특정 경로 ID를 통해 경로를 논리적으로 삭제 처리합니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공 (응답 본문 없음)")
    @ApiResponse(responseCode = "404", description = "경로를 찾을 수 없음")
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(
            @Parameter(description = "삭제 할 경로 ID", example = "1", required = true)
            @PathVariable Long routeId){
        routeService.deleteRoute(routeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
