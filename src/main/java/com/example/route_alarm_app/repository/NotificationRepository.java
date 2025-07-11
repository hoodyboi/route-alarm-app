package com.example.route_alarm_app.repository;

import com.example.route_alarm_app.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserUserIdOrderByCreatedAtDesc(Long userId);

}
