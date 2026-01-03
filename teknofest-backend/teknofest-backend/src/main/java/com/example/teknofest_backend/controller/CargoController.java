package com.example.teknofest_backend.controller;

import ch.qos.logback.core.model.Model;
import com.example.teknofest_backend.entity.Cargo;
import com.example.teknofest_backend.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(path="cargo")
public class CargoController{

    private final CargoService cargoService;
    /*private static final Logger log = LoggerFactory.getLogger(CargoController.class);
    private final Path imagesLocation = Paths.get("C:/Users/Asus/Desktop/teknofest-backend/teknofest-backend/src/main/resources/ui-files");*/

    @Autowired
    public CargoController(CargoService cargoService){

        this.cargoService = cargoService;
    }


    @GetMapping(path = "get")
    public List<Cargo> getAllCargo(){

        return cargoService.getCargo();
    }



    @PostMapping(path = "add")
    public ResponseEntity<String> addCargo(@RequestBody Cargo cargo){

        cargoService.addNewCargo(cargo);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"success\":true}");
    }


    @DeleteMapping(path = "{receiver}")
    public ResponseEntity<String> deleteCargo(@PathVariable("receiver") String receiver){

        cargoService.deleteCargo(receiver);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"success\":true}");
    }



    /*
    @GetMapping(path = "approve")
    public List<Cargo> approveCargo(){

        cargoService.pythonExecuter();
        return cargoService.getCargo();
    }

     */

    /*
    //Static file loading in html format. (It doesn't load the .ui files, just downloads them.)
    @GetMapping("/ui-files/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        log.info("Accessing file {}", filename);
        try {
            Path file = imagesLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\""); // Display in browser

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
     */
}
