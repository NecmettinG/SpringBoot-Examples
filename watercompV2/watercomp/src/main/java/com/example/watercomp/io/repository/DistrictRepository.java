package com.example.watercomp.io.repository;

import com.example.watercomp.io.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {

    Optional<DistrictEntity> findByDistrictName(String districtName);

    boolean existsByDistrictName(String districtName);
}

