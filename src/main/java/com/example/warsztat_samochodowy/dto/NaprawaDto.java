package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Mechanik;

public class NaprawaDto {
    private Mechanik mechanik;
    private int naprawaID;

    public NaprawaDto(int naprawaID) {
        this.naprawaID = naprawaID;
    }

    public NaprawaDto(Mechanik mechanik, int naprawaID) {
        this.mechanik = mechanik;
        this.naprawaID = naprawaID;
    }

    public Mechanik getMechanik() {
        return mechanik;
    }

    public void setMechanik(Mechanik mechanik) {
        this.mechanik = mechanik;
    }

    public int getNaprawaID() {
        return naprawaID;
    }

    public void setNaprawaID(int naprawaID) {
        this.naprawaID = naprawaID;
    }
}
