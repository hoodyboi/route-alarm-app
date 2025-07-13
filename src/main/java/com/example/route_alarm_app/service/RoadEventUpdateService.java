package com.example.route_alarm_app.service;

import com.example.route_alarm_app.client.PublicDataApiClient;
import com.example.route_alarm_app.domain.RoadEvent;
import com.example.route_alarm_app.dto.AccInfoResponse;
import com.example.route_alarm_app.dto.AccInfoRow;
import com.example.route_alarm_app.repository.RoadEventRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.proj4j.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadEventUpdateService {

    private final PublicDataApiClient apiClient;
    private final RoadEventRepository roadEventRepository;
    private final GeometryFactory geometryFactory;

    private CoordinateTransform coordinateTransform;

    @PostConstruct
    public void init() {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem grs80 = crsFactory.createFromName("EPSG:5181");
        CoordinateReferenceSystem wgs84 = crsFactory.createFromName("EPSG:4326");
        this.coordinateTransform = new CoordinateTransformFactory().createTransform(grs80, wgs84);
    }

    /**
     1시간에 한 번씩 공공 API를 호출하여 도로 이벤트 정보를 업데이트합니다.
     */
    @Scheduled(cron = "0 0 * * * *") // 매시 정각에 실행
    @Transactional
    public void updateRoadEventsFromApi() {
        AccInfoResponse response = apiClient.getAccidentInfo();
        if (response == null || response.getRows() == null) {
            return;
        }

        List<AccInfoRow> rows = response.getRows();

        for (AccInfoRow row : rows){
            Long accId = Long.parseLong(row.getAccId());
            Point wgs84Point = convertTmToWgs84(row.getGrs80tmX(), row.getGrs80tmY());
            if(wgs84Point == null) continue;

            Optional<RoadEvent> existingEventOpt = roadEventRepository.findByAccId(accId);

            if(existingEventOpt.isPresent()){
                RoadEvent existingEvent = existingEventOpt.get();
                existingEvent.update(row, wgs84Point);
            } else {
                RoadEvent newEvent = RoadEvent.builder()
                        .accId(accId)
                        .eventType(row.getAccType())
                        .location(wgs84Point)
                        .description(row.getAccInfo())
                        .build();
                roadEventRepository.save(newEvent);
            }
        }
    }

    private Point convertTmToWgs84(double tmX, double tmY) {
        ProjCoordinate before = new ProjCoordinate(tmX, tmY);
        ProjCoordinate after = new ProjCoordinate();

        coordinateTransform.transform(before, after);

        Point point = geometryFactory.createPoint(new Coordinate(after.x, after.y));

        return point;
    }
}