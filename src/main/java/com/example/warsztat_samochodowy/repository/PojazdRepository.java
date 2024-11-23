package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Pojazd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PojazdRepository extends JpaRepository<Pojazd, String> {


    Optional<Pojazd> findByPojazdID(String PojazdID);
    Optional<Pojazd> findByVin(String VIN);
}
