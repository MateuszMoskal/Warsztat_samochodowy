package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Naprawa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NaprawaRepository extends JpaRepository<Naprawa, Integer> {


    Optional<Naprawa> findByNaprawaID(int naprawaID);
}
