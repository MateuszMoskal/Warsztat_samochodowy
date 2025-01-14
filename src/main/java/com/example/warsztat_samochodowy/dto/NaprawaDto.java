package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Mechanik;

public class NaprawaDto {
    private String mechanikLogin;
    private int naprawaID;

    public NaprawaDto(String mechanikLogin, int naprawaID) {
        this.mechanikLogin = mechanikLogin;
        this.naprawaID = naprawaID;
    }

    public String getMechanikLogin() {
        return mechanikLogin;
    }

    public void setMechanikLogin(String mechanikLogin) {
        this.mechanikLogin = mechanikLogin;
    }

    public int getNaprawaID() {
        return naprawaID;
    }

    public void setNaprawaID(int naprawaID) {
        this.naprawaID = naprawaID;
    }
}
