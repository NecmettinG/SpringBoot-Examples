package com.example.stockmarket.controller;

import com.example.stockmarket.entity.Asset;
import com.example.stockmarket.service.AssetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "asset")
public class AssetController{


    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService){

        this.assetService = assetService;
    }

    @GetMapping(path = "{customerId}")
    public List<Asset> getAllAssets(@PathVariable("customerId") Long customerId){

        return assetService.getAssets(customerId);
    }

    @PostMapping
    public void addAsset(@RequestBody Asset asset){

        assetService.addAssets(asset);
    }



}
