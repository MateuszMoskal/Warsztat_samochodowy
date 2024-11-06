package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Klient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KlientRepository extends JpaRepository<Klient, String> {


    Optional<Klient> findByKlientID(int klientID);
    Optional<Klient> findByTelefon(String telefon);
}

