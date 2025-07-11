package com.example.route_alarm_app.service;

import com.example.route_alarm_app.dto.NotificationResponseDto;
import com.example.route_alarm_app.service.RoadEventUpdateService;
import com.example.route_alarm_app.domain.Notification;
import com.example.route_alarm_app.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationResponseDto> getNotificationsForUser(Long userId){
        List<Notification> notifications = notificationRepository.findAllByUserUserIdOrderByCreatedAtDesc(userId);

        return notifications.stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());

    }
}
