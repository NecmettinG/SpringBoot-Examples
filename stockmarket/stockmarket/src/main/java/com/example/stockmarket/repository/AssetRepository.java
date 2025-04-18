package com.example.stockmarket.repository;

import com.example.stockmarket.entity.Asset;
import com.example.stockmarket.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>{

    List<Asset> findAssetByCustomerId(Long customerId);
    Optional<Asset> findAssetByAssetNameAndCustomerId(String assetName, Long customerId);

}
