package com.example.watercomp.io.entity;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity(name="neighbourhood_table")
public class NeighbourhoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String neighbourhoodName;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @ManyToOne
    @JoinColumn(name = "district_id") //id of the user that owns the address will be stored in foreign key called "users_id".
    private DistrictEntity districtId;

    public String getNeighbourhoodName() {
        return neighbourhoodName;
    }

    public void setNeighbourhoodName(String neighbourhoodName) {
        this.neighbourhoodName = neighbourhoodName;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public DistrictEntity getDistrictId() {
        return districtId;
    }

    public void setDistrictId(DistrictEntity districtId) {
        this.districtId = districtId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
