-- 회원 (User) 테이블 (oauth_type, oauth_id, uuid 컬럼 모두 유지)
CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    login_id VARCHAR(16) UNIQUE NOT NULL,
    email VARCHAR(256) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    oauth_type VARCHAR(20), -- 유지
    oauth_id VARCHAR(64),   -- 유지
    uuid VARCHAR(36) UNIQUE -- CHAR(36) 대신 VARCHAR(36)으로 변경, Unique 유지
);

-- 경로 관리 (Route) 테이블 (uuid 컬럼 유지)
CREATE TABLE IF NOT EXISTS routes (
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
    uuid CHAR(36) UNIQUE, -- 유지
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 도로 이벤트 (RoadEvent) 테이블
CREATE TABLE IF NOT EXISTS road_events (
    road_event_id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(20) NOT NULL,
    location VARCHAR(100) NOT NULL, -- GEOMETRY 대신 VARCHAR(100)으로 변경
    severity INT,
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 경로별 알림 설정 (RouteAlarmSetting) 테이블 (route_id만 PK이자 FK, user_id 없음)
CREATE TABLE IF NOT EXISTS route_alarm_settings(
    route_id BIGINT PRIMARY KEY, -- Route 테이블의 route_id를 참조하는 PK이자 FK
    protest_alert BOOLEAN NOT NULL DEFAULT TRUE,
    construction_alert BOOLEAN NOT NULL DEFAULT TRUE,
    congestion_alert BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (route_id) REFERENCES routes(route_id) ON DELETE CASCADE -- route_id 참조
);

-- 알림 이력 (Notification) 테이블 (route_id 존재, route_alarm_id 없음)
CREATE TABLE IF NOT EXISTS notifications (
    notification_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    road_event_id BIGINT NOT NULL,
    route_id BIGINT NOT NULL, -- route_id 컬럼 유지, FK
    -- route_alarm_id 컬럼은 ERD에 없음
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    message VARCHAR(256) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (road_event_id) REFERENCES road_events(road_event_id),
    FOREIGN KEY (route_id) REFERENCES routes(route_id) -- 외래 키
);