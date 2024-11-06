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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VIN", referencedColumnName = "VIN")
    //private String VIN; // klucz obcy
    private Pojazd pojazd;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mechanikID", referencedColumnName = "mechanikID")
    //private int mechanik; // klucz obcy
    private Mechanik mechanik;

    public Naprawa(Pojazd pojazd, Mechanik mechanik) {
        this.pojazd = pojazd;
        this.mechanik = mechanik;
    }

    public Naprawa(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public Naprawa() {
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
}
