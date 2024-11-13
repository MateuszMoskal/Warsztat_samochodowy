package com.example.warsztat_samochodowy.controller;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.service.Mechanik_serwis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class MechanikController {

    private final Mechanik_serwis mechanik_serwis;

    public MechanikController(Mechanik_serwis mechanik_serwis) {
        this.mechanik_serwis = mechanik_serwis;
    }

    @PostMapping("/dodaj/nowe_zgloszenie")
    public ResponseEntity<Naprawa> Nowe_zgloszenie(@RequestBody Naprawa naprawa, Mechanik mechanik){
        try{
            Naprawa zaakceptowana_naprawa = mechanik_serwis.Przyjecie_naprawy(naprawa, mechanik);
            return ResponseEntity.ok(zaakceptowana_naprawa);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(naprawa);
        }
    }

    @PatchMapping("/modyfikuj/opis_usterki")
    public ResponseEntity<Naprawa> Modyfikacja_opisu_usterki(@RequestBody int NaprawaID, String opis_usterki, String stan, String protokol_naprawy){
        Naprawa naprawa = mechanik_serwis.Modyfikacja_opisu_usterki(NaprawaID, opis_usterki, stan, protokol_naprawy);
        return ResponseEntity.ok(naprawa);
    }

    @PatchMapping("/modyfikuj/rozpoczecie_naprawy")
    public ResponseEntity<Naprawa> Rozpoczecie_naprawy(@RequestBody int NaprawaID, Date data_rozpoczecia){
        Naprawa naprawa = mechanik_serwis.Rozpoczecie_naprawy(NaprawaID, data_rozpoczecia);
        return ResponseEntity.ok(naprawa);
    }

    @PatchMapping("/modyfikuj/zakonczenie_naprawy")
    public ResponseEntity<Naprawa> Zakonczenie_naprawy(@RequestBody int NaprawaID, Date data_zakonczenia){
        Naprawa naprawa = mechanik_serwis.Rozpoczecie_naprawy(NaprawaID, data_zakonczenia);
        return ResponseEntity.ok(naprawa);
    }


}
