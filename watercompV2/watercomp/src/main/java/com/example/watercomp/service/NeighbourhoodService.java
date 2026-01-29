package com.example.watercomp.service;

import com.example.watercomp.io.entity.DistrictEntity;
import com.example.watercomp.io.entity.NeighbourhoodEntity;
import com.example.watercomp.io.repository.DistrictRepository;
import com.example.watercomp.io.repository.NeighbourhoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NeighbourhoodService {

    @Autowired
    private NeighbourhoodRepository neighbourhoodRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public List<NeighbourhoodEntity> getAllNeighbourhoods() {
        return neighbourhoodRepository.findAll();
    }

    public Optional<NeighbourhoodEntity> getNeighbourhoodById(Long id) {
        return neighbourhoodRepository.findById(id);
    }

    public Optional<NeighbourhoodEntity> getNeighbourhoodByName(String name) {
        return neighbourhoodRepository.findByNeighbourhoodName(name);
    }

    public List<NeighbourhoodEntity> getNeighbourhoodsByDistrictId(Long districtId) {
        return neighbourhoodRepository.findByDistrictId_Id(districtId);
    }

    public NeighbourhoodEntity createNeighbourhood(NeighbourhoodEntity neighbourhood, Long districtId) {
        if (districtId != null) {
            DistrictEntity district = districtRepository.findById(districtId)
                    .orElseThrow(() -> new RuntimeException("District not found with id: " + districtId));
            neighbourhood.setDistrictId(district);
        }
        return neighbourhoodRepository.save(neighbourhood);
    }

    public NeighbourhoodEntity updateNeighbourhood(Long id, NeighbourhoodEntity neighbourhoodDetails) {
        NeighbourhoodEntity neighbourhood = neighbourhoodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Neighbourhood not found with id: " + id));

        neighbourhood.setNeighbourhoodName(neighbourhoodDetails.getNeighbourhoodName());
        neighbourhood.setLocation(neighbourhoodDetails.getLocation());

        return neighbourhoodRepository.save(neighbourhood);
    }

    public void deleteNeighbourhood(Long id) {
        NeighbourhoodEntity neighbourhood = neighbourhoodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Neighbourhood not found with id: " + id));
        neighbourhoodRepository.delete(neighbourhood);
    }

    public boolean existsByName(String name) {
        return neighbourhoodRepository.existsByNeighbourhoodName(name);
    }
}

