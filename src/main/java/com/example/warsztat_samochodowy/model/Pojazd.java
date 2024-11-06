package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Pojazdy")
public class Pojazd {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pojazdID = UUID.randomUUID().toString();
    private String rejestracja;
    private String marka;
    private String model;
    private int rocznik;
    @Id
    private String VIN; // klucz podstawowy
    @ManyToOne
    @JoinColumn(name = "klientID", insertable = false, updatable = false)
    private Klient klient;

    public Pojazd(String rejestracja, String marka, String model, int rocznik, String VIN) {
        this.rejestracja = rejestracja;
        this.marka = marka;
        this.model = model;
        this.rocznik = rocznik;
        this.VIN = VIN;
    }

    public Pojazd() {
    }

    public String getPojazdID() {
        return pojazdID;
    }

    public void setPojazdID(String ID) {
        this.pojazdID = ID;
    }

    public String getRejestracja() {
        return rejestracja;
    }

    public void setRejestracja(String rejestracja) {
        this.rejestracja = rejestracja;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRocznik() {
        return rocznik;
    }

    public void setRocznik(int rocznik) {
        this.rocznik = rocznik;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }
}
