package com.example.warsztat_samochodowy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Klienci")

public class Klient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int klientID;
    @Column(nullable = false)
    private String imie;
    @Column(nullable = false)
    private String nazwisko;
    @Column(nullable = false, unique = true)
    private String telefon;
    @Column(nullable = false)
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "klientID")
    private List<Pojazd> pojazdy = new ArrayList<>();

    public Klient(String imie, String nazwisko, String telefon, String email) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.email = email;
    }

    public Klient() {
    }

    public int getKlientID() {
        return klientID;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setKlientID(int KlientID) {

        this.klientID = KlientID;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
