package com.example.watercomp.ui.controller;

import com.example.watercomp.io.entity.DistrictEntity;
import com.example.watercomp.io.entity.NeighbourhoodEntity;
import com.example.watercomp.service.DistrictService;
import com.example.watercomp.service.NeighbourhoodService;
import com.example.watercomp.shared.dto.DistrictRequestDTO;
import com.example.watercomp.shared.dto.DistrictResponseDTO;
import com.example.watercomp.shared.dto.NeighbourhoodRequestDTO;
import com.example.watercomp.shared.dto.NeighbourhoodResponseDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private NeighbourhoodService neighbourhoodService;

    // Koordinat sistemi oluşturucu (SRID 4326: Standart GPS)
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    // ==================== DISTRICT ENDPOINTS ====================

    /**
     * Tüm ilçeleri getir
     */
    @GetMapping
    public ResponseEntity<List<DistrictResponseDTO>> getAllDistricts() {
        List<DistrictEntity> districts = districtService.getAllDistricts();
        List<DistrictResponseDTO> response = districts.stream()
                .map(this::convertToDistrictResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * ID'ye göre ilçe getir
     */
    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponseDTO> getDistrictById(@PathVariable Long id) {
        return districtService.getDistrictById(id)
                .map(this::convertToDistrictResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * İsme göre ilçe getir
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<DistrictResponseDTO> getDistrictByName(@PathVariable String name) {
        return districtService.getDistrictByName(name)
                .map(this::convertToDistrictResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Yeni ilçe oluştur
     */
    @PostMapping
    public ResponseEntity<DistrictResponseDTO> createDistrict(@RequestBody DistrictRequestDTO request) {
        if (districtService.existsByName(request.getDistrictName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        DistrictEntity district = new DistrictEntity();
        district.setDistrictName(request.getDistrictName());

        DistrictEntity createdDistrict = districtService.createDistrict(district);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDistrictResponseDTO(createdDistrict));
    }

    /**
     * İlçe güncelle
     */
    @PutMapping("/{id}")
    public ResponseEntity<DistrictResponseDTO> updateDistrict(@PathVariable Long id, @RequestBody DistrictRequestDTO request) {
        try {
            DistrictEntity districtDetails = new DistrictEntity();
            districtDetails.setDistrictName(request.getDistrictName());

            DistrictEntity updatedDistrict = districtService.updateDistrict(id, districtDetails);
            return ResponseEntity.ok(convertToDistrictResponseDTO(updatedDistrict));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * İlçe sil
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        try {
            districtService.deleteDistrict(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ==================== NEIGHBOURHOOD ENDPOINTS ====================

    /**
     * Tüm mahalleleri getir
     */
    @GetMapping("/neighbourhoods")
    public ResponseEntity<List<NeighbourhoodResponseDTO>> getAllNeighbourhoods() {
        List<NeighbourhoodEntity> neighbourhoods = neighbourhoodService.getAllNeighbourhoods();
        List<NeighbourhoodResponseDTO> response = neighbourhoods.stream()
                .map(this::convertToNeighbourhoodResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * ID'ye göre mahalle getir
     */
    @GetMapping("/neighbourhoods/{id}")
    public ResponseEntity<NeighbourhoodResponseDTO> getNeighbourhoodById(@PathVariable Long id) {
        return neighbourhoodService.getNeighbourhoodById(id)
                .map(this::convertToNeighbourhoodResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * İsme göre mahalle getir
     */
    @GetMapping("/neighbourhoods/name/{name}")
    public ResponseEntity<NeighbourhoodResponseDTO> getNeighbourhoodByName(@PathVariable String name) {
        return neighbourhoodService.getNeighbourhoodByName(name)
                .map(this::convertToNeighbourhoodResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Belirli bir ilçeye ait mahalleleri getir
     */
    @GetMapping("/{districtId}/neighbourhoods")
    public ResponseEntity<List<NeighbourhoodResponseDTO>> getNeighbourhoodsByDistrict(@PathVariable Long districtId) {
        List<NeighbourhoodEntity> neighbourhoods = neighbourhoodService.getNeighbourhoodsByDistrictId(districtId);
        List<NeighbourhoodResponseDTO> response = neighbourhoods.stream()
                .map(this::convertToNeighbourhoodResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Yeni mahalle oluştur
     */
    @PostMapping("/{districtId}/neighbourhoods")
    public ResponseEntity<NeighbourhoodResponseDTO> createNeighbourhood(
            @PathVariable Long districtId,
            @RequestBody NeighbourhoodRequestDTO request) {
        try {
            NeighbourhoodEntity neighbourhood = new NeighbourhoodEntity();
            neighbourhood.setNeighbourhoodName(request.getNeighbourhoodName());

            // DİKKAT: GeometryFactory önce X (Boylam/Lon), sonra Y (Enlem/Lat) ister!
            if (request.getLat() != null && request.getLon() != null) {
                Point point = geometryFactory.createPoint(new Coordinate(request.getLon(), request.getLat()));
                neighbourhood.setLocation(point);
            }

            NeighbourhoodEntity createdNeighbourhood = neighbourhoodService.createNeighbourhood(neighbourhood, districtId);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToNeighbourhoodResponseDTO(createdNeighbourhood));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Mahalle güncelle
     */
    @PutMapping("/neighbourhoods/{id}")
    public ResponseEntity<NeighbourhoodResponseDTO> updateNeighbourhood(
            @PathVariable Long id,
            @RequestBody NeighbourhoodRequestDTO request) {
        try {
            NeighbourhoodEntity neighbourhoodDetails = new NeighbourhoodEntity();
            neighbourhoodDetails.setNeighbourhoodName(request.getNeighbourhoodName());

            // DİKKAT: GeometryFactory önce X (Boylam/Lon), sonra Y (Enlem/Lat) ister!
            if (request.getLat() != null && request.getLon() != null) {
                Point point = geometryFactory.createPoint(new Coordinate(request.getLon(), request.getLat()));
                neighbourhoodDetails.setLocation(point);
            }

            NeighbourhoodEntity updatedNeighbourhood = neighbourhoodService.updateNeighbourhood(id, neighbourhoodDetails);
            return ResponseEntity.ok(convertToNeighbourhoodResponseDTO(updatedNeighbourhood));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Mahalle sil
     */
    @DeleteMapping("/neighbourhoods/{id}")
    public ResponseEntity<Void> deleteNeighbourhood(@PathVariable Long id) {
        try {
            neighbourhoodService.deleteNeighbourhood(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ==================== CONVERTER METHODS ====================

    private DistrictResponseDTO convertToDistrictResponseDTO(DistrictEntity entity) {
        DistrictResponseDTO dto = new DistrictResponseDTO();
        dto.setId(entity.getId());
        dto.setDistrictName(entity.getDistrictName());
        return dto;
    }

    private NeighbourhoodResponseDTO convertToNeighbourhoodResponseDTO(NeighbourhoodEntity entity) {
        NeighbourhoodResponseDTO dto = new NeighbourhoodResponseDTO();
        dto.setId(entity.getId());
        dto.setNeighbourhoodName(entity.getNeighbourhoodName());

        // Point'ten lat/lon çıkar
        if (entity.getLocation() != null) {
            dto.setLat(entity.getLocation().getY()); // Y = Latitude
            dto.setLon(entity.getLocation().getX()); // X = Longitude
        }

        // İlçe bilgisini ekle
        if (entity.getDistrictId() != null) {
            dto.setDistrictId(entity.getDistrictId().getId());
            dto.setDistrictName(entity.getDistrictId().getDistrictName());
        }

        return dto;
    }
}
