package com.example.watercomp.io.repository;

import com.example.watercomp.io.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<SensorData, Long> {

    // ST_DistanceSphere: İki nokta arasındaki mesafeyi METRE cinsinden hesaplar.
    // Hackathon için en hayat kurtarıcı fonksiyondur.
    @Query(value = "SELECT * FROM sensor_data s " +
            "WHERE ST_DistanceSphere(s.location, ST_MakePoint(:lon, :lat)) <= :radiusMeters",
            nativeQuery = true)
    List<SensorData> findSensorsNearby(@Param("lat") double lat,
                                       @Param("lon") double lon,
                                       @Param("radiusMeters") double radiusMeters);
}
