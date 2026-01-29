package com.example.watercomp.io.repository;

import com.example.watercomp.io.entity.NeighbourhoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NeighbourhoodRepository extends JpaRepository<NeighbourhoodEntity, Long> {

    List<NeighbourhoodEntity> findByDistrictId_Id(Long districtId);

    Optional<NeighbourhoodEntity> findByNeighbourhoodName(String neighbourhoodName);

    boolean existsByNeighbourhoodName(String neighbourhoodName);
}

