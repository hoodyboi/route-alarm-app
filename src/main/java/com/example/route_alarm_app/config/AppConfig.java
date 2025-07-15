package com.example.route_alarm_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

    @Bean
    @Primary  // 이 ObjectMapper를 기본으로 사용하도록 설정
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // JavaTimeModule 등록 (LocalDateTime 처리)
        mapper.registerModule(new JavaTimeModule());

        // 타임스탬프 형태로 직렬화하지 않고 ISO 형태로 직렬화
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 알 수 없는 속성이 있어도 실패하지 않도록 설정
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper;
    }

    @Bean
    public XmlMapper xmlMapper(){

        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}