package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Mechanicy")
public class Mechanik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mechanikID;
    @Column(nullable = false)
    private String imie;
    @Column(nullable = false)
    private String nazwisko;
    //private int naprawy;


    public Mechanik(String nazwisko, String imie) {
        this.nazwisko = nazwisko;
        this.imie = imie;
    }

    public Mechanik() {
    }

    public int getMechanikID() {
        return mechanikID;
    }

    public void setMechanikID(int mechanikID) {
        this.mechanikID = mechanikID;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
