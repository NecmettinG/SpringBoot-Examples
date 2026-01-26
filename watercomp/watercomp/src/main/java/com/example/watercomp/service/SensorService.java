package com.example.watercomp.service;

import com.example.watercomp.io.entity.SensorData;
import com.example.watercomp.io.repository.SensorRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SensorService {

    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Autowired
    SensorRepository sensorRepository;

    public void saveSensorData(double lat, double lon, double moisture) {
        SensorData data = new SensorData();
        data.setSoilMoisture(moisture);

        // Lon (Boylam) önce, Lat (Enlem) sonra gelir! (X, Y mantığı)
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        data.setLocation(point);
        data.setTimestamp(LocalDateTime.now());

        sensorRepository.save(data);
    }
}
