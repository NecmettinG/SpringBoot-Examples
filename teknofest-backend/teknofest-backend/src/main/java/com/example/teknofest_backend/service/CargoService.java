package com.example.teknofest_backend.service;

import com.example.teknofest_backend.entity.Cargo;
import com.example.teknofest_backend.repository.CargoRepository;
import com.example.teknofest_backend.repository.CargoSpecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CargoService{

    private final CargoRepository cargoRepository;
    private final CargoSpecialRepository cargoSpecialRepository;
    private int rowCount = 0;


    @Autowired
    public CargoService(CargoRepository cargoRepository, CargoSpecialRepository cargoSpecialRepository) {

        this.cargoRepository = cargoRepository;
        this.cargoSpecialRepository = cargoSpecialRepository;
    }

    public List<Cargo> getCargo(){

        return cargoRepository.findAll();
    }

    public void addNewCargo(Cargo cargo){

        Optional<Cargo> cargoOptional = cargoSpecialRepository.findCargoByReceiver(cargo.getReceiver());

        if(cargoOptional.isPresent()){

            throw new IllegalStateException("Cargo already exists");
        }

        else {
            if (rowCount < 2) {
                //cargoRepository.deleteAll();
                cargoRepository.save(cargo);
                rowCount++;
            }
            else {
                throw new IllegalStateException("Too many cargos");
            }
        }
    }

    public void deleteCargo(String receiver){

        Cargo cargo = cargoRepository.findCargoByReceiver(receiver);

        if(cargo == null){
            throw new IllegalStateException("Cargo does not exist");
        }

        cargoRepository.delete(cargo);
        rowCount--;

    }

    @Async //It will be executed asynchronously so we won't wait this function to finish its job.
    public void pythonExecuter(){

        try{

            ProcessBuilder pb = new ProcessBuilder("python", "C:/Users/Asus/Desktop/Teknofest/teknofest/telem_code2.py");

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
        }

        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
