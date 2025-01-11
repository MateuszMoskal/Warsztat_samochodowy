package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Naprawy")
public class Naprawa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int naprawaID;
    private Date data_rozpoczecia;
    private Date data_zakonczenia;
    private String stan;
    private String opis_usterki;
    private String protokol_naprawy;
    private String telefon_klienta;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VIN")
    //private String VIN; // klucz obcy
    private Pojazd pojazd;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mechanikID")
    //private int mechanik; // klucz obcy
    private Mechanik mechanik;

    public Naprawa(Pojazd pojazd, Mechanik mechanik) {
        this.pojazd = pojazd;
        this.mechanik = mechanik;
        this.telefon_klienta = pojazd.getKlient().getTelefon();
    }

    public Naprawa(Pojazd pojazd) {
        this.pojazd = pojazd;
        this.telefon_klienta = pojazd.getKlient().getTelefon();
    }

    public Naprawa() {
    }

    public int getNaprawaID() {
        return naprawaID;
    }

    public Date getData_rozpoczecia() {
        return data_rozpoczecia;
    }

    public Date getData_zakonczenia() {
        return data_zakonczenia;
    }

    public String getStan() {
        return stan;
    }

    public String getOpis_usterki() {
        return opis_usterki;
    }

    public String getProtokol_naprawy() {
        return protokol_naprawy;
    }

    public Pojazd getPojazd() {
        return pojazd;
    }

    public Mechanik getMechanik() {
        return mechanik;
    }

    public void setNaprawaID(int naprawaID) {
        this.naprawaID = naprawaID;
    }

    public void setData_rozpoczecia(Date data_rozpoczecia) {
        this.data_rozpoczecia = data_rozpoczecia;
    }

    public void setData_zakonczenia(Date data_zakonczenia) {
        this.data_zakonczenia = data_zakonczenia;
    }

    public void setOpis_usterki(String opis_usterki) {
        this.opis_usterki = opis_usterki;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public void setProtokol_naprawy(String protokol_naprawy) {
        this.protokol_naprawy = protokol_naprawy;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public void setMechanik(Mechanik mechanik) {
        this.mechanik = mechanik;
    }

    public String getTelefon_klienta() {
        return telefon_klienta;
    }

    public void setTelefon_klienta(String telefon_klienta) {
        this.telefon_klienta = telefon_klienta;
    }
}
