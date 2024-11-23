package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    @Column(nullable = false)
    private String czyZatrudniony;
    //private int naprawy;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mechanikID")
    private List<Naprawa> naprawy = new ArrayList<Naprawa>();


    public Mechanik(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.czyZatrudniony = "TAK";
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

    public String getCzyZatrudniony() {
        return czyZatrudniony;
    }

    public void setCzyZatrudniony(String czyZatrudniony) {
        this.czyZatrudniony = czyZatrudniony;
    }
}
