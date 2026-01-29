package com.example.watercomp.io.entity;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Entity(name="district_table")
public class DistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String districtName;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtId", orphanRemoval = true)
    private List<NeighbourhoodEntity> neighbourhoodEntities;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    public List<NeighbourhoodEntity> getNeighbourhoodEntities() {
        return neighbourhoodEntities;
    }

    public void setNeighbourhoodEntities(List<NeighbourhoodEntity> neighbourhoodEntities) {
        this.neighbourhoodEntities = neighbourhoodEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
