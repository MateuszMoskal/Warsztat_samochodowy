package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Pojazd;

public class PojazdKlientDto {
    private Pojazd pojazd;
    private String telefonKlienta;

    public Pojazd getPojazd() {
        return pojazd;
    }

    public String getTelefonKlienta() {
        return telefonKlienta;
    }
}
