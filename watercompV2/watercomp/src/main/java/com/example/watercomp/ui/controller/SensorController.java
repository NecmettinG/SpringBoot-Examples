package com.example.watercomp.ui.controller;


import com.example.watercomp.io.entity.SensorData;
import com.example.watercomp.io.repository.SensorRepository;
import com.example.watercomp.shared.dto.SensorRequestDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    // Koordinat sistemi oluşturucu (SRID 4326: Standart GPS)
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @PostMapping("/save")
    public SensorData saveSensor(@RequestBody SensorRequestDTO request) {
        SensorData data = new SensorData();
        data.setSoilMoisture(request.getMoisture());
        data.setTimestamp(LocalDateTime.now());

        // DİKKAT: GeometryFactory önce X (Boylam/Lon), sonra Y (Enlem/Lat) ister!
        // Ters yaparsan sensörün Konya yerine Hint Okyanusu'nda çıkar :)
        Point point = geometryFactory.createPoint(new Coordinate(request.getLon(), request.getLat()));
        data.setLocation(point);

        return sensorRepository.save(data);
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensors() {
        return sensorRepository.findAll();
    }

    @GetMapping("/nearby")
    public List<SensorData> getNearbySensors(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double dist) { // dist: metre cinsinden yarıçap

        return sensorRepository.findSensorsNearby(lat, lon, dist);
    }
}
