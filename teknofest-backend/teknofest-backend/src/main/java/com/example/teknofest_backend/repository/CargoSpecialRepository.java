package com.example.teknofest_backend.repository;

import com.example.teknofest_backend.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoSpecialRepository extends JpaRepository<Cargo, Long>{

    Optional<Cargo> findCargoByReceiver(String receiver);
}
