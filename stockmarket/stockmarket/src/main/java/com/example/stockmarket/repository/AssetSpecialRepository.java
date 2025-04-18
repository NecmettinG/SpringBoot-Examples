package com.example.stockmarket.repository;

import com.example.stockmarket.entity.Asset;
import com.example.stockmarket.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetSpecialRepository extends JpaRepository<Asset, Long>{

    Asset findAssetByAssetNameAndCustomerId(String assetName, Long customerId);
}

//I had to create this repository...
