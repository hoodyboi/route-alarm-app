package com.example.route_alarm_app;

import com.example.route_alarm_app.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class RouteAlarmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteAlarmAppApplication.class, args);
	}

}
