package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Pojazd;

public class PojazdKlientDto {
    private Pojazd pojazd;
    private String telefon;

    public Pojazd getPojazd() {
        return pojazd;
    }

    public String getTelefon() {
        return telefon;
    }
}
