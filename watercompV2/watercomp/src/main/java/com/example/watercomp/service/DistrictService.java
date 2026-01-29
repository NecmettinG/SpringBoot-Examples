package com.example.watercomp.service;

import com.example.watercomp.io.entity.DistrictEntity;
import com.example.watercomp.io.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    public List<DistrictEntity> getAllDistricts() {
        return districtRepository.findAll();
    }

    public Optional<DistrictEntity> getDistrictById(Long id) {
        return districtRepository.findById(id);
    }

    public Optional<DistrictEntity> getDistrictByName(String name) {
        return districtRepository.findByDistrictName(name);
    }

    public DistrictEntity createDistrict(DistrictEntity district) {
        return districtRepository.save(district);
    }

    public DistrictEntity updateDistrict(Long id, DistrictEntity districtDetails) {
        DistrictEntity district = districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found with id: " + id));

        district.setDistrictName(districtDetails.getDistrictName());

        return districtRepository.save(district);
    }

    public void deleteDistrict(Long id) {
        DistrictEntity district = districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found with id: " + id));
        districtRepository.delete(district);
    }

    public boolean existsByName(String name) {
        return districtRepository.existsByDistrictName(name);
    }
}

