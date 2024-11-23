package com.example.warsztat_samochodowy.controller;


import com.example.warsztat_samochodowy.dto.PojazdKlientDto;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.service.Warsztat_serwis;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarsztatController {

    private final Warsztat_serwis warsztat_serwis;

    public WarsztatController(Warsztat_serwis warsztat_serwis) {
        this.warsztat_serwis = warsztat_serwis;
    }
    @GetMapping("/klienci")
    public List<Klient> Wyswietl_klientow(){
        List<Klient> lista_klientow = warsztat_serwis.Podglad_klientow();
        return lista_klientow;
    }

    @PostMapping(value = "/dodaj/klienta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Klient> Dodaj_klienta(@RequestBody Klient klient){
        Klient nowy_klient = warsztat_serwis.Dodawanie_klienta(klient);
        return ResponseEntity.ok(nowy_klient);
    }

    @PatchMapping("/modyfikuj/dane/klienta")
    public ResponseEntity<Klient> Modyfikuj_dane_klienta(@RequestBody Klient klient){
        warsztat_serwis.Modyfikacje_danych_klienta(klient);
        return ResponseEntity.ok(klient);
    }

    @GetMapping("/mechanicy")
    public List<Mechanik> Wyswietl_mechanikow(){
        List<Mechanik> lista_mechanikow = warsztat_serwis.Podglad_mechanikow();
        return lista_mechanikow;
    }

    @PostMapping("/dodaj/mechanika")
    public ResponseEntity<Mechanik> Dodaj_mechanika(@RequestBody Mechanik mechanik){
        Mechanik nowy_mechanik = warsztat_serwis.Dodawanie_mechanika(mechanik);
        return ResponseEntity.ok(nowy_mechanik);
    }

    @PatchMapping("/zwolnij/mechanika")
    public ResponseEntity<Mechanik> Zwolnij_mechanika(@RequestBody Mechanik mechanik){
        warsztat_serwis.Zwolnienie_mechanika(mechanik);
        return ResponseEntity.ok(mechanik);
    }

    @GetMapping("/naprawy")
    public List<Naprawa> Wyswietl_naprawy(){
        List<Naprawa> lista_napraw = warsztat_serwis.Podglad_napraw();
        return lista_napraw;
    }

    @PostMapping("/dodaj/naprawe")
    public ResponseEntity<Naprawa> Dodaj_naprawe(@RequestBody Naprawa naprawa){
        try{
            Naprawa nowa_naprawa = warsztat_serwis.Dodawanie_naprawy(naprawa);
            return ResponseEntity.ok(nowa_naprawa);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(naprawa);
        }
    }

    @GetMapping("/pojazdy")
    public List<Pojazd> Wyswietl_pojazd(){
        List<Pojazd> lista_pojazdow = warsztat_serwis.Podglad_pojazdow();
        return lista_pojazdow;
    }

    @PostMapping("/dodaj/pojazdy")
    public ResponseEntity<Pojazd> Dodaj_pojazd(@RequestBody PojazdKlientDto pojazdKlientDto){
        Pojazd nowy_pojazd = warsztat_serwis.Dodawanie_pojazdu(pojazdKlientDto.getPojazd(),pojazdKlientDto.getTelefon());
        return ResponseEntity.ok(nowy_pojazd);
    }

    @PatchMapping("/modyfikuj/dane/pojazdow")
    public ResponseEntity<Pojazd> Modyfikuj_pojazd(@RequestBody Pojazd pojazd){
        warsztat_serwis.Modyfikacje_danych_pojazdu(pojazd);
        return ResponseEntity.ok(pojazd);
    }

    @PostMapping("/dodaj/nowe/zgloszenie")
    public ResponseEntity<Naprawa> Nowe_zgloszenie(@RequestBody Klient klient, Pojazd pojazd){
        try{
            Naprawa naprawa = warsztat_serwis.Dodanie_nowego_zgloszenia(klient, pojazd);
            return ResponseEntity.ok(naprawa);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
