package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Pojazd;

public class ZgloszenieDto {
    private Klient klient;
    private Pojazd pojazd;

    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }
}
