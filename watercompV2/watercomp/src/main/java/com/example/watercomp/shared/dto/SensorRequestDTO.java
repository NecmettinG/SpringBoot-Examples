package com.example.watercomp.shared.dto;

public class SensorRequestDTO {

    private Double lat;
    private Double lon;
    private Double moisture;

    // Getter ve Setter'lar
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }

    public Double getMoisture() { return moisture; }
    public void setMoisture(Double moisture) { this.moisture = moisture; }
}
