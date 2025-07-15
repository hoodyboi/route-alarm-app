package com.example.route_alarm_app.controller;

import com.example.route_alarm_app.service.RoadEventUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final RoadEventUpdateService roadEventUpdateService;

    @PostMapping("/sync-road-events")
    public ResponseEntity<String> syncRoadEvents() {
        roadEventUpdateService.updateRoadEventsFromApi();
        return ResponseEntity.ok("Road event data sync started.");
    }
}