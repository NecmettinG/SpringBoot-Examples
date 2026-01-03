package com.example.teknofest_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Cargo")
public class Cargo{

    @Id
    @SequenceGenerator(
            name = "cargo_sequence",
            sequenceName = "cargo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cargo_sequence"
    )
    private long id;

    private String receiver;
    private String coordinate;

    @Enumerated(EnumType.STRING)
    private CargoType cargoType;

    public Cargo() {}

    public Cargo(String receiver, String coordinate, CargoType cargoType) {

        this.receiver = receiver;
        this.coordinate = coordinate;
        this.cargoType = cargoType;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getReceiver(){
        return receiver;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public String getCoordinate(){
        return coordinate;
    }

    public void setCoordinate(String coordinate){
        this.coordinate = coordinate;
    }

    public CargoType getCargoType(){

        return cargoType;
    }

    public void setCargoType(CargoType cargoType){

        this.cargoType = cargoType;
    }


    @Override
    public String toString(){

        return "Cargo{" +
                "id=" + id +
                ", receiver= " + receiver + '\'' +
                ", coordinate= " + coordinate + '\'' +
                ", cargo type= " + cargoType + '}';
    }


}
