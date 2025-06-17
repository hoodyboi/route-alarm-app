-- 회원 (User) 테이블
CREATE TABLE IF NOT EXISTS "User" (
    user_id BIGSERIAL PRIMARY KEY,
    login_id VARCHAR(16) UNIQUE NOT NULL,
    email VARCHAR(256) UNIQUE,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    oauth_type VARCHAR(20),
    oauth_id VARCHAR(64),
    uuid CHAR(36) UNIQUE
);

-- 경로 관리 (Route) 테이블
CREATE TABLE IF NOT EXISTS Route (
    route_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    route_name VARCHAR(50) NOT NULL,
    src_lat DECIMAL(10,7) NOT NULL,
    src_lng DECIMAL(10,7) NOT NULL,
    dst_lat DECIMAL(10,7) NOT NULL,
    dst_lng DECIMAL(10,7) NOT NULL,
    waypoints JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    uuid CHAR(36) UNIQUE,
    FOREIGN KEY (user_id) REFERENCES "User"(user_id)
);

-- 도로 이벤트 (RoadEvent) 테이블
CREATE TABLE IF NOT EXISTS RoadEvent (
    road_event_id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(20) NOT NULL,
    lat DECIMAL(10,7) NOT NULL,
    lng DECIMAL(10,7) NOT NULL,
    severity INT,
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 경로별 알림 설정 (RouteAlarmSetting) 테이블
CREATE TABLE IF NOT EXISTS RouteAlarmSetting(
    route_alarm_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    route_id BIGINT NOT NULL,
    protest_alert BOOLEAN NOT NULL DEFAULT TRUE,
    construction_alert BOOLEAN NOT NULL DEFAULT TRUE,
    congestion_alert BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "User"(user_id),
    FOREIGN KEY (route_id) REFERENCES Route(route_id)
);

-- 알림 이력 (Notification) 테이블
CREATE TABLE IF NOT EXISTS Notification (
    notification_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    road_event_id BIGINT NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    message VARCHAR(256) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "User"(user_id),
    FOREIGN KEY (road_event_id) REFERENCES RoadEvent(road_event_id)
);